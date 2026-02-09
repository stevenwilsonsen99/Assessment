package com.example.BuzzHealthTask.service;

import com.example.BuzzHealthTask.request.CouponRequest;
import com.example.BuzzHealthTask.request.PriceQuoteRequest;
import com.example.BuzzHealthTask.response.CouponResponse;
import com.example.BuzzHealthTask.response.PriceQuoteResponse;

import java.util.List;
import java.util.UUID;

public interface CouponService {

    CouponResponse create(CouponRequest request);

    CouponResponse getById(UUID id);

    List<CouponResponse> getAll();

    CouponResponse update(UUID id, CouponRequest request);

    void delete(UUID id);

    PriceQuoteResponse getPriceQuote(UUID couponId, PriceQuoteRequest request);
}


