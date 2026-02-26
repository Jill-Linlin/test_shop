package com.shop.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name="product")
@Data
public class Product {
    @Id
    @Column(name="product_id")
    private String productId;

    @Column(name="product_name")
    private String productName;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="stock_quantity")
    private Integer stockQuantity;

    @Column(name="image_url")
    private String imageUrl;
    
}
