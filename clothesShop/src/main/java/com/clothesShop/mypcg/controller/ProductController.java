package com.clothesShop.mypcg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clothesShop.mypcg.auth.AuthenticationService;
import com.clothesShop.mypcg.dto.ProductSaleRequest;
import com.clothesShop.mypcg.dto.ProductSaleResponse;
import com.clothesShop.mypcg.entity.Product;
import com.clothesShop.mypcg.service.ProductService;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final AuthenticationService authService;

    public ProductController(ProductService productService, AuthenticationService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    private String getLoggedInUsername(HttpServletRequest request) {
        // Retrieve the username from the request or session, based on your authentication mechanism
        // For example, if you store the authenticated username in the session attribute
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (String) session.getAttribute("authenticatedUser");
        }
        
        // If you are using a different authentication mechanism, adapt this method accordingly
        
        return null;
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/sell/{productId}")
    public ResponseEntity<ProductSaleResponse> sellProduct(@PathVariable int productId, @RequestBody ProductSaleRequest request) {
        try {
            Product product = productService.sellProduct(request);
            ProductSaleResponse response = new ProductSaleResponse(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

