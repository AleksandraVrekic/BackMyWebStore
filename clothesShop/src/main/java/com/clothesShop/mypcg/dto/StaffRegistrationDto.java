package com.clothesShop.mypcg.dto;

import com.clothesShop.mypcg.entity.Role;

public class StaffRegistrationDto {
    private String name;
    private String surname;
    private String email;
    private String userName;
    private String password;
    private String position;
    private Role role; // Novo polje za ulogu

    // Constructors
    public StaffRegistrationDto() {
    }

    public StaffRegistrationDto(String name, String surname, String email, String userName, String password, String position,  Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.position = position;
        this.role = role;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}

