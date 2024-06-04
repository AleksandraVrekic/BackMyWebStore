package com.clothesShop.mypcg.dto;

public class CustomerUpdateDto {
    private String phone;
    private AddressDto address;
    private AccountDto account;

    // Getters and Setters for CustomerUpdateDto
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    // Nested DTO for Address
    public static class AddressDto {
        private Integer addressId;
        private String street;
        private String country;
        private String city;
        private Integer zip;

        // Getters and Setters for AddressDto
        public Integer getAddressId() {
            return addressId;
        }

        public void setAddressId(Integer addressId) {
            this.addressId = addressId;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getZip() {
            return zip;
        }

        public void setZip(Integer zip) {
            this.zip = zip;
        }
    }

    // Nested DTO for Account
    public static class AccountDto {
        private Integer id;
        private String userName;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String userRole;

        // Getters and Setters for AccountDto
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }
    }
}
