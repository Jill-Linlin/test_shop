package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.OrderMain;

public interface OrderMainRepository extends JpaRepository<OrderMain,String> {
    
}
