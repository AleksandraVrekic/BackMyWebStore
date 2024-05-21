package com.clothesShop.mypcg.dto;

import com.clothesShop.mypcg.entity.Role;

public class RegistrationRequestDTO {
    private String username;
    private String password;
    private Role role;

    public RegistrationRequestDTO() {
    }

    public RegistrationRequestDTO(String username, String password, Role role) {
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

    public Role getUserRole() {
        return role;
    }

    public void setUserRole(Role role) {
        this.role = role;
    }
}

