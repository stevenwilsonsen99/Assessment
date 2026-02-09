package com.example.BuzzHealthTask.repo;

import com.example.BuzzHealthTask.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CouponRepo extends JpaRepository<Coupon, UUID> {
}
