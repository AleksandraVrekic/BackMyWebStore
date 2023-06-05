package com.clothesShop.mypcg.dto;

public class RegistrationRequestDTO {
    private String username;
    private String password;
    private String role;

    public RegistrationRequestDTO() {
    }

    public RegistrationRequestDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role=role;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return role;
    }

    public void setUserRole(String role) {
        this.role = role;
    }
}

