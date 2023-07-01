package com.clothesShop.mypcg.dto;

import com.clothesShop.mypcg.entity.Product;

public class ProductSaleResponse {
    private Product product;
    
    public ProductSaleResponse(Product product) {
        this.product = product;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
}

