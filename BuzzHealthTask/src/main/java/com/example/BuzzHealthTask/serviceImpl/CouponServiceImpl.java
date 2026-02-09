package com.example.BuzzHealthTask.serviceImpl;

import com.example.BuzzHealthTask.entity.Coupon;
import com.example.BuzzHealthTask.entity.Drug;
import com.example.BuzzHealthTask.entity.Pharmacy;
import com.example.BuzzHealthTask.enums.DiscountType;
import com.example.BuzzHealthTask.exception.BadRequestException;
import com.example.BuzzHealthTask.exception.ResourceNotFoundException;
import com.example.BuzzHealthTask.repo.CouponRepo;
import com.example.BuzzHealthTask.repo.DrugRepo;
import com.example.BuzzHealthTask.repo.PharmacyRepo;
import com.example.BuzzHealthTask.request.CouponRequest;
import com.example.BuzzHealthTask.request.PriceQuoteRequest;
import com.example.BuzzHealthTask.response.CouponResponse;
import com.example.BuzzHealthTask.response.PriceQuoteResponse;
import com.example.BuzzHealthTask.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepo couponRepo;
    private final DrugRepo drugRepo;
    private final PharmacyRepo pharmacyRepo;

    private static final BigDecimal HUNDRED = new BigDecimal("100");

    @Override
    @Transactional
    public CouponResponse create(CouponRequest request) {
        Coupon coupon = mapToEntity(new Coupon(), request);
        return mapToResponse(couponRepo.save(coupon));
    }

    @Override
    public CouponResponse getById(UUID id) {
        Coupon coupon = couponRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id: " + id));
        return mapToResponse(coupon);
    }

    @Override
    public List<CouponResponse> getAll() {
        return couponRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CouponResponse update(UUID id, CouponRequest request) {
        Coupon coupon = couponRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id: " + id));

        mapToEntity(coupon, request);
        return mapToResponse(couponRepo.save(coupon));
    }

    @Override
    public void delete(UUID id) {
        if (!couponRepo.existsById(id)) {
            throw new ResourceNotFoundException("Coupon not found with id: " + id);
        }
        couponRepo.deleteById(id);
    }

    @Override
    public PriceQuoteResponse getPriceQuote(UUID couponId, PriceQuoteRequest request) {

        if (request.quantity() == null || request.quantity() <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id: " + couponId));

        if (!Boolean.TRUE.equals(coupon.getIsActive())) {
            throw new BadRequestException("Coupon is not active");
        }

        LocalDate today = LocalDate.now();
        if (today.isBefore(coupon.getStartDate()) || today.isAfter(coupon.getEndDate())) {
            throw new BadRequestException("Coupon is expired");
        }

        BigDecimal quantity = BigDecimal.valueOf(request.quantity());
        BigDecimal priceBefore = coupon.getCashPrice().multiply(quantity);
        BigDecimal discountedUnitPrice = calculateDiscountedUnitPrice(coupon);
        BigDecimal priceAfter = discountedUnitPrice.multiply(quantity);

        return new PriceQuoteResponse(priceBefore, priceAfter);
    }


    private BigDecimal calculateDiscountedUnitPrice(Coupon coupon) {
        BigDecimal cashPrice = coupon.getCashPrice();
        BigDecimal minPrice = coupon.getMinPrice();
        BigDecimal discounted;

        if (coupon.getDiscountType() == DiscountType.FLAT) {
            discounted = cashPrice.subtract(coupon.getDiscountValue());
        } else {
            BigDecimal percent = coupon.getDiscountValue().divide(HUNDRED, 4, RoundingMode.HALF_UP);
            discounted = cashPrice.multiply(BigDecimal.ONE.subtract(percent));
        }

        return discounted.max(minPrice);
    }

    private Coupon mapToEntity(Coupon coupon, CouponRequest request) {

        Drug drug = drugRepo.findById(request.drugId())
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found with id: " + request.drugId()));

        Pharmacy pharmacy = pharmacyRepo.findById(request.pharmacyId())
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found with id: " + request.pharmacyId()));

        coupon.setDrug(drug);
        coupon.setPharmacy(pharmacy);
        coupon.setBin(request.bin());
        coupon.setPcn(request.pcn());
        coupon.setMemberId(request.memberId());
        coupon.setGroupId(request.groupId());
        coupon.setCashPrice(request.cashPrice());
        coupon.setDiscountType(request.discountType());
        coupon.setDiscountValue(request.discountValue());
        coupon.setMinPrice(request.minPrice());
        coupon.setStartDate(request.startDate());
        coupon.setEndDate(request.endDate());
        coupon.setIsActive(request.isActive());
        coupon.setCreatedAt(LocalDateTime.now());
        return coupon;
    }

    private CouponResponse mapToResponse(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getDrug().getId(),
                coupon.getPharmacy().getId(),
                coupon.getCashPrice(),
                coupon.getDiscountType(),
                coupon.getIsActive(),
                coupon.getCreatedAt()
        );
    }
}
