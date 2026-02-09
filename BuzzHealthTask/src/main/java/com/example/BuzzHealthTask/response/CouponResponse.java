package com.example.BuzzHealthTask.response;

import com.example.BuzzHealthTask.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CouponResponse(
        UUID id,
        UUID drugId,
        UUID pharmacyId,
        BigDecimal cashPrice,
        DiscountType discountType,
        Boolean isActive,
        LocalDateTime createdAt
) {}

