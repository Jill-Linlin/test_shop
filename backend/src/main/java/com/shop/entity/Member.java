package com.shop.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name="member")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Integer memberId;

    @Column(name="full_name")
    private String fullName;

    @Column(name="email")
    private String email;

    
    @Column(name = "create_time",insertable = false, updatable = false)
    private LocalDateTime createTime;


    
}
