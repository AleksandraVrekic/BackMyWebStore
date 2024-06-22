package com.clothesShop.mypcg.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.clothesShop.mypcg.auth.AuthenticationService;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.entity.Staff;
import com.clothesShop.mypcg.repository.AccountRepository;
import com.clothesShop.mypcg.repository.StaffRepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    public StaffController(StaffRepository staffRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix

        if (!authService.isAdmin(token) && !authService.isSuperAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Staff> staffList = staffRepository.findAll();
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Integer id) {
        Optional<Staff> staff = staffRepository.findById(id);
        return staff.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*
    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        Account account = staff.getAccount();
        if (account != null && account.getId() != null) {
            // Pronađi postojeći nalog iz baze podataka
            Account existingAccount = accountRepository.findById(account.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + account.getId()));
            // Poveži postojeći nalog sa Staff entitetom
            staff.setAccount(existingAccount);
        } else if (account != null) {
            // Ako je nalog nov, hashuj lozinku
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountRepository.save(account);
        }

        // Sačuvaj Staff entitet
        Staff savedStaff = staffRepository.save(staff);
        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }*/
    
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@RequestHeader("Authorization") String tokenHeader, @PathVariable Integer id, @RequestBody Staff staffDetails) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix

        if (!authService.isSuperAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            Account account = staff.getAccount();

            // Check for existing username
            if (!account.getUserName().equals(staffDetails.getAccount().getUserName()) && accountRepository.existsByuserName(staffDetails.getAccount().getUserName())) {
                return new ResponseEntity<>("Username already exists!", HttpStatus.CONFLICT);
            }

            // Check for existing email
            if (!account.getEmail().equals(staffDetails.getAccount().getEmail()) && accountRepository.existsByEmail(staffDetails.getAccount().getEmail())) {
                return new ResponseEntity<>("Email already exists!", HttpStatus.CONFLICT);
            }

            account.setFirstName(staffDetails.getAccount().getFirstName());
            account.setLastName(staffDetails.getAccount().getLastName());
            account.setEmail(staffDetails.getAccount().getEmail());
            account.setUserName(staffDetails.getAccount().getUserName());
            if (staffDetails.getAccount().getPassword() != null && !staffDetails.getAccount().getPassword().isEmpty()) {
                account.setPassword(passwordEncoder.encode(staffDetails.getAccount().getPassword())); // Hashovanje lozinke
            }
            staff.setAccount(account);
            staff.setPosition(staffDetails.getPosition());
            Staff updatedStaff = staffRepository.save(staff);
            return new ResponseEntity<>(updatedStaff, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@RequestHeader("Authorization") String tokenHeader, @PathVariable Integer id) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix

        if (!authService.isSuperAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isPresent()) {
            staffRepository.delete(staffOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

}
