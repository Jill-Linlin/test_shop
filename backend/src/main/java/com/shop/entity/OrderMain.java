package com.shop.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="order_main")
@Data
public class OrderMain {
    @Id
    @Column(name="order_id")
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="total_amount")
    private BigDecimal totalAmount;

    @Column(name="create_time", insertable = false, updatable = false)
    private LocalDateTime createTime;
    
}
