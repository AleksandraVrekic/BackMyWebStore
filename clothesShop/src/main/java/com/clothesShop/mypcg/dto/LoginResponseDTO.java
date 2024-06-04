package com.clothesShop.mypcg.dto;

public class LoginResponseDTO {
    private String token;
    private Integer id;
    private String role;
    private String firstName;
    private String lastName;
    private String email;

    // Constructor, getters, and setters
    public LoginResponseDTO(String token, Integer id, String role, String firstName, String lastName, String email) {
        this.token = token;
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
