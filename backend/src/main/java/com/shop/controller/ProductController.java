package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entity.Product;
import com.shop.repository.ProductRepository;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/products")//test
public class ProductController {
    @Autowired
    private ProductRepository ProductRepository;

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return ProductRepository.findAll();
    }
    
    
}
