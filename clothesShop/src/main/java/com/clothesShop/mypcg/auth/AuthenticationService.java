package com.clothesShop.mypcg.auth;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    boolean authenticate(String username, String password);
    String generateToken(String username);
    boolean isAuthenticated(String username, HttpServletRequest request);
    boolean isManager(HttpServletRequest request);
    
}