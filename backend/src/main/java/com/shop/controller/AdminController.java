package com.shop.controller;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.entity.Product;
import com.shop.repository.ProductRepository;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/admin/products")
public class AdminController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products=productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/upload/";
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<String> addProduct(
        @RequestParam("productId") String productId,
        @RequestParam("productName") String productName,
        @RequestParam("price") BigDecimal price,
        @RequestParam("stockQuantity") Integer stockQuantity,
        @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
        // 預設為空字串，如果沒傳照片，SP 接收到的就是 ""
        String imageUrlForSp = ""; 

        // 1. 處理檔案 (如果有上傳)
        if (image != null && !image.isEmpty()) {
            String originalName = image.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
            
            // 強制命名規範：ID.jpg
            imageUrlForSp = productId + extension;
            
            Path path = Paths.get(UPLOAD_DIR + imageUrlForSp);
            Files.write(path, image.getBytes());
        }
        productRepository.addProductBySp(
            productId, 
            productName, 
            price, 
            stockQuantity, 
            imageUrlForSp
        );

        return ResponseEntity.ok("商品新增成功 (透過 SP)");

        } catch (Exception e) {
         return ResponseEntity.status(500).body("系統錯誤: " + e.getMessage());
        }
    }    
    
}
