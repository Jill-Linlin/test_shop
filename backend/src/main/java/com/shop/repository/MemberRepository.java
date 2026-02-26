package com.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByEmail(String email);//透過email確認是否有此用戶
}
