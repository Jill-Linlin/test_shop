package com.shop.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

//前端傳來的物件(會員編號+購買品項id、數量)

@Data
public class OrderRequest {
    
    @NotBlank(message = "Email is empty!")
    @Email(message = "Email type error")
    private String email;
    
    @NotBlank(message = "Name is not Empty")
    private String fullName;
    
    @NotEmpty(message = "Shopping car is not Empty")
    private List<ItemRequest> items;

    @Data
    public static class ItemRequest {
        @NotBlank(message = "Id is empty!")
        private String productId;
        
        @Min(value = 1,message="Quantity at least 1")
        private Integer quantity;
    }
    
}
