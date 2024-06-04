package com.clothesShop.mypcg.auth;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clothesShop.mypcg.dto.LoginRequestDTO;
import com.clothesShop.mypcg.dto.LoginResponseDTO;
import com.clothesShop.mypcg.dto.RegistrationRequestDTO;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.exception.AuthenticationException;
import com.clothesShop.mypcg.exception.RegistrationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        
        // Authenticate user credentials
        boolean authenticated = authService.authenticate(username, password);
        
        if (authenticated) {
            // Generate token
            String token = authService.generateToken(username);
            
            // Retrieve user details
            Account account = authService.findAccountByUsername(username); // Assume this method exists
            
            // Prepare and return the response with account details
            LoginResponseDTO response = new LoginResponseDTO(
                token,
                account.getId(),
                account.getUserRole().toString(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail()
            );
            
            return ResponseEntity.ok(response);
        } else {
            // Return unauthorized status if authentication fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO registrationRequest) {
        try {
            authService.registerUser(registrationRequest);
            return ResponseEntity.ok("Uspešno ste se registrovali.");
        } catch (RegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // Retrieve the token from the request headers
        String token = extractTokenFromHeaders(request);

        // Invalidate the token by removing it from the authentication service
        authService.invalidateToken(token);

        // Set the Content-Type header to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Return a success response with JSON body
        return ResponseEntity.ok().headers(headers).body("{\"message\": \"Logout successful\"}");
    }

    private String extractTokenFromHeaders(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove the "Bearer " prefix to get the token
        }
        return null;
    }
    
}
