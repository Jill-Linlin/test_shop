package com.shop.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="detail_id")
    private Integer detailId;
    
    @Column(name="order_id")
    private String orderId;
    
    @Column(name="product_id")
    private String productId;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="unit_price")
    private BigDecimal unitPrice;

}
