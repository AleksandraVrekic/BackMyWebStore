package com.clothesShop.mypcg.entity;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
	@SequenceGenerator(name="PRODUCT_ID_GENERATOR", sequenceName="PRODUCT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_ID_GENERATOR")
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;
    
    @Column(name = "quantity") 
    private Integer quantity;

    @Column(name = "image")
    private String image;

	@ManyToOne()
	@JoinColumn(name="category_id")
	private Category category;

    // Constructors, getters, and setters

    public Product() {
    }

    public Product(String name, String description, double price, Integer quantity, String image, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.category = category;
    }

    // Getters and Setters for productId, name, description, price, quantity, image, and categoryId
    

    public Integer getId() {
        return productId;
    }

    public void setId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
