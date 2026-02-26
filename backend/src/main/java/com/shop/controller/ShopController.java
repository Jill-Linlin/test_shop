package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entity.Product;
import com.shop.repository.ProductRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("api/shop")
@CrossOrigin(origins = "http://localhost:5173")
public class ShopController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")//取得大於0總數的產品
    @Transactional
    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> products=productRepository.spGetAvailableProducts();
        return ResponseEntity.ok(products);
    }
    
    
}
