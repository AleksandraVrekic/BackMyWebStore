package com.clothesShop.mypcg.auth;

import javax.servlet.http.HttpServletRequest;

import com.clothesShop.mypcg.exception.AuthenticationException;

public interface AuthenticationService {
    boolean authenticate(String username, String password) throws AuthenticationException;
    String generateToken(String username);
    boolean isAuthenticated(String username, HttpServletRequest request);
    boolean isManager(HttpServletRequest request);
    
}
