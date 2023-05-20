package com.clothesShop.mypcg.auth;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.repository.AccountRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    
    public AuthenticationServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean authenticate(String username, String password) {
        // Retrieve user from the repository based on the username
        Optional<Account> accountOptional = accountRepository.findByUserName(username);
        
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            // Compare the provided password with the account's actual password
            return password.equals(account.getPassword());
        }
        
        return false;
    }
    
    
    private static final long TOKEN_EXPIRATION_TIME = 86400000; // 24 hours in milliseconds
    Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    @Override
    public String generateToken(String username) {
        // Set the token expiration time

    	
        long expirationTimeMillis = System.currentTimeMillis() + TOKEN_EXPIRATION_TIME;
        
        // Create the claims for the token
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        
        // Build the JWT
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256));
        
        // Generate the token
        String token = jwtBuilder.compact();
        
        return token;
    }
    
    // Implement more methods for user-related operations
    
}
