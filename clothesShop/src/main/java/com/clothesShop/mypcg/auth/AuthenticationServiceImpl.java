package com.clothesShop.mypcg.auth;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.dto.RegistrationRequestDTO;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.entity.Role;
import com.clothesShop.mypcg.exception.AuthenticationException;
import com.clothesShop.mypcg.exception.RegistrationException;
import com.clothesShop.mypcg.repository.AccountRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private Set<String> tokenBlacklist = new HashSet<>();
    private static final long TOKEN_EXPIRATION_TIME = 86400000; // 24 sata u milisekundama
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generišemo tajni ključ

    public AuthenticationServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean isAdmin(String token) {
        if (tokenBlacklist.contains(token)) {
            return false; // Token je na crnoj listi
        }
        return validateTokenRole(token, Role.ADMIN);
    }

    @Override
    public boolean isCustomer(String token) {
        if (tokenBlacklist.contains(token)) {
            return false; // Token je na crnoj listi
        }
        return validateTokenRole(token, Role.CUSTOMER);
    }

    private boolean validateTokenRole(String token, Role role) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY) // koristi generisani ključ za verifikaciju
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();

            Optional<Account> accountOptional = accountRepository.findByUserName(username);
            return accountOptional.isPresent() && accountOptional.get().getUserRole().equals(role);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void invalidateToken(String token) {
        tokenBlacklist.add(token); // dodaj token na crnu listu
    }

    @Override
    public boolean authenticate(String username, String password) {
        Optional<Account> accountOptional = accountRepository.findByUserName(username);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (password.equals(account.getPassword())) {
                return true;
            } else {
                throw new AuthenticationException("Invalid password");
            }
        }

        throw new AuthenticationException("User not found");
    }

    public String generateToken(String username) {
        long expirationTimeMillis = System.currentTimeMillis() + TOKEN_EXPIRATION_TIME;
        
        // Pretpostavimo da imate metod koji vraća korisničku ulogu
        Role role = getUserRole(username);

        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role.name()) // Konvertuje Role u String
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(SECRET_KEY)
                .compact();

        return token;
    }

    public Role getUserRole(String username) {
        Account account = accountRepository.findByuserName(username);
        if (account != null) {
            return account.getUserRole();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }



    @Override
    public boolean isAuthenticated(String username, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("authenticatedUser") != null) {
            String authenticatedUsername = (String) session.getAttribute("authenticatedUser");
            return username.equals(authenticatedUsername);
        }

        return false;
    }

    @Override
    public void registerUser(RegistrationRequestDTO registrationRequest) throws RegistrationException {
        if (accountRepository.findByUserName(registrationRequest.getUsername()).isPresent()) {
            throw new RegistrationException("Korisničko ime već postoji.");
        }

        Account account = new Account();
        account.setUserName(registrationRequest.getUsername());
        account.setPassword(registrationRequest.getPassword());
        account.setUserRole(registrationRequest.getUserRole());

        accountRepository.save(account);
    }
    
    @Override
    public boolean validateToken(String token) {
        if (tokenBlacklist.contains(token)) {
            return false; // Token je na crnoj listi
        }
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Account> accountOptional = accountRepository.findByUserName(username);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return new org.springframework.security.core.userdetails.User(account.getUserName(), account.getPassword(), getAuthorities(account));
        }
        throw new UsernameNotFoundException("User not found");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Account account) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + account.getUserRole().name()));
    }
    
    @Override
    public Account findAccountByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUserName(username)
                                 .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}


