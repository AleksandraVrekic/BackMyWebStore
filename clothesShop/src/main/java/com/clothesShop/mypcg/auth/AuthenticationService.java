package com.clothesShop.mypcg.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.clothesShop.mypcg.dto.RegistrationRequestDTO;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.exception.AuthenticationException;
import com.clothesShop.mypcg.exception.RegistrationException;

public interface AuthenticationService {
    boolean authenticate(String username, String password);
    String generateToken(String username);
    boolean isAuthenticated(String token, HttpServletRequest request);
    boolean isAdmin(String token);
    boolean isSuperAdmin(String token);
    boolean isCustomer(String token); // Dodata metoda za proveru da li je korisnik Customer
    void registerUser(RegistrationRequestDTO registrationRequest) throws RegistrationException;
    void invalidateToken(String token);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    Account findAccountByUsername(String username) throws UsernameNotFoundException; //
   
}
