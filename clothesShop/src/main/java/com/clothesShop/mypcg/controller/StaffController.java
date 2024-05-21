package com.clothesShop.mypcg.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public StaffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }
    
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Integer id) {
        Optional<Staff> staff = staffRepository.findById(id);
        return staff.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        Account account = staff.getAccount();
        if (account != null && account.getId() != null) {
            // Pronađi postojeći nalog iz baze podataka
            Account existingAccount = accountRepository.findById(account.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + account.getId()));
            // Poveži postojeći nalog sa Staff entitetom
            staff.setAccount(existingAccount);
        }

        // Sačuvaj Staff entitet
        Staff savedStaff = staffRepository.save(staff);
        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Integer id, @RequestBody Staff updatedStaff) {
        Optional<Staff> optionalStaff = staffRepository.findById(id);
        if (optionalStaff.isPresent()) {
            Staff staff = optionalStaff.get();
            staff.setPosition(updatedStaff.getPosition());
            staff.setAccount(updatedStaff.getAccount());
            Staff savedStaff = staffRepository.save(staff);
            return new ResponseEntity<>(savedStaff, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Integer id) {
        if (staffRepository.existsById(id)) {
            staffRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
