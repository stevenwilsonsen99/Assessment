package com.example.BuzzHealthTask.request;

import com.example.BuzzHealthTask.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CouponRequest(
        UUID drugId,
        UUID pharmacyId,
        String bin,
        String pcn,
        String memberId,
        String groupId,
        BigDecimal cashPrice,
        DiscountType discountType,
        BigDecimal discountValue,
        BigDecimal minPrice,
        LocalDate startDate,
        LocalDate endDate,
        Boolean isActive
) {}

