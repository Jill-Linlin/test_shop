package com.shop.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.OrderRequest;
import com.shop.entity.*;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderDetailRepository;
import com.shop.repository.OrderMainRepository;
import com.shop.repository.ProductRepository;

@Service
public class OrderService {
    @Autowired 
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMainRepository orderMainRepository;
    @Autowired
    private MemberRepository memberRepository;


    //建立訂單
    @Transactional
    public String PlaceOrder (OrderRequest request){
    //確認購買人是否重複

    Member member=memberRepository.findByEmail(request.getEmail()).map(existingMember -> {
        if(!existingMember.getFullName().equals(request.getFullName())){ //當帳號存在但姓名邊更的時候會自動儲存新名字
            existingMember.setFullName(request.getFullName());
            return memberRepository.save(existingMember);
        }
        return existingMember;    
    }).orElseGet(()->{
        //新建member
        Member newMember=new Member();
        newMember.setEmail(request.getEmail());//set email
        newMember.setFullName(request.getFullName());//set fullname
        return memberRepository.save(newMember);
    });
    //生成單號

    String dateStr=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String orderId="Ms"+dateStr+String.format("%04d",(int)(Math.random()*10000));//"%04d"不足4位會自動補0

    BigDecimal totalAmount=BigDecimal.ZERO;
    for(OrderRequest.ItemRequest item:request.getItems()){
    //檢查商品
    Product product=productRepository.findById(item.getProductId()).orElseThrow(()->new RuntimeException("product doesn't exist"+item.getProductId()));

    if (product.getStockQuantity() < item.getQuantity()) {
            throw new RuntimeException("product: " + product.getProductName() + " out of stock!");
        }
    //計算總金額
    BigDecimal itemTotal=product.getPrice().multiply(new BigDecimal(item.getQuantity()));
    totalAmount=totalAmount.add(itemTotal);
    }

    //存入ordermain
    OrderMain orderMain=new OrderMain();
    orderMain.setOrderId(orderId);
    orderMain.setTotalAmount(totalAmount);
    orderMain.setMember(member);
    orderMainRepository.save(orderMain);



    for(OrderRequest.ItemRequest item:request.getItems()){
        //檢查商品
        Product product=productRepository.findById(item.getProductId()).orElseThrow(()->new RuntimeException("product doesn't exist"+item.getProductId()));
        product.setStockQuantity(product.getStockQuantity()-item.getQuantity());
        productRepository.saveAndFlush(product);

        //存入orderdetail
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setProductId(product.getProductId());
        orderDetail.setOrderId(orderId);
        orderDetail.setQuantity(item.getQuantity());
        orderDetail.setUnitPrice(product.getPrice());
        orderDetailRepository.save(orderDetail);

    }
    return orderId;
    }
    
    
    
}
