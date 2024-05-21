package com.clothesShop.mypcg.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clothesShop.mypcg.entity.Address;
import com.clothesShop.mypcg.exception.ResourceNotFoundException;
import com.clothesShop.mypcg.repository.AddressRepository;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // Vrati sve adrese
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    // Vrati adresu po ID-u
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return new ResponseEntity<>(address.get(), HttpStatus.OK);
        } else {
        	throw new ResourceNotFoundException("Ne postoji adresa sa id: " + id);
        }
    }

    // Kreiraj novu adresu
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
    	if(!addressRepository.existsById(address.getAddressId())) {
    		Address newAddress = addressRepository.save(address);
    		return new ResponseEntity<Address>(HttpStatus.OK);
    		}
    	return new ResponseEntity<Address>(HttpStatus.CONFLICT); 
    }

    // Azuriraj adresu
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address addressDetails) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setCity(addressDetails.getCity());
            address.setCountry(addressDetails.getCountry());
            address.setStreet(addressDetails.getStreet());
            address.setZip(addressDetails.getZip());
            Address updatedAddress = addressRepository.save(address);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
    }

    // Izbrisi adresu po ID-u
    @DeleteMapping("/{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable Integer id) {
		if (!addressRepository.existsById(id)) {
			return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
		}
		
		addressRepository.deleteById(id);		
		return new ResponseEntity<Address>(HttpStatus.OK);
    }
}