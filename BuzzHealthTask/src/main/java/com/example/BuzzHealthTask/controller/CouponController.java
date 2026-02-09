package com.example.BuzzHealthTask.controller;

import com.example.BuzzHealthTask.request.CouponRequest;
import com.example.BuzzHealthTask.request.PriceQuoteRequest;
import com.example.BuzzHealthTask.response.BaseResponse;
import com.example.BuzzHealthTask.response.CouponResponse;
import com.example.BuzzHealthTask.response.PriceQuoteResponse;
import com.example.BuzzHealthTask.service.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CouponResponse>> create(
            @RequestBody CouponRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(
                        "Coupon created successfully",
                        couponService.create(request),
                        HttpStatus.CREATED.value()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CouponResponse>> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Coupon fetched successfully",
                        couponService.getById(id),
                        HttpStatus.OK.value()
                )
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CouponResponse>>> getAll() {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Coupons fetched successfully",
                        couponService.getAll(),
                        HttpStatus.OK.value()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CouponResponse>> update(
            @PathVariable UUID id,
            @RequestBody CouponRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Coupon updated successfully",
                        couponService.update(id, request),
                        HttpStatus.OK.value()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @PathVariable UUID id
    ) {
        couponService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.success("Coupon deleted successfully", null, HttpStatus.OK.value())
        );
    }

    @PostMapping("/{id}/price-quote")
    public ResponseEntity<BaseResponse<PriceQuoteResponse>> getPriceQuote(
            @PathVariable UUID id,
            @RequestBody PriceQuoteRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Price quote calculated successfully",
                        couponService.getPriceQuote(id, request),
                        HttpStatus.OK.value()
                )
        );
    }
}
