package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.OrderRequest;
import com.shop.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderservice;

    @PostMapping("/create")//新增訂單
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderRequest request) {
        //TODO: process POST request
        try{
            String orderId=orderservice.PlaceOrder(request);
            return ResponseEntity.ok("create success!!"+orderId);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body("create error"+e.getMessage()); 
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("System error");
        }
    }
    
    
}
