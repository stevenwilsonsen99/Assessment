package com.example.BuzzHealthTask.controller;

import com.example.BuzzHealthTask.request.PharmacyRequest;
import com.example.BuzzHealthTask.response.BaseResponse;
import com.example.BuzzHealthTask.response.PharmacyResponse;
import com.example.BuzzHealthTask.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @PostMapping
    public ResponseEntity<BaseResponse<PharmacyResponse>> create(
            @RequestBody PharmacyRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(
                        "Pharmacy created successfully",
                        pharmacyService.create(request),
                        HttpStatus.CREATED.value()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PharmacyResponse>> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Pharmacy fetched successfully",
                        pharmacyService.getById(id),
                        HttpStatus.OK.value()
                )
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<PharmacyResponse>>> getAll() {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Pharmacies fetched successfully",
                        pharmacyService.getAll(),
                        HttpStatus.OK.value()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<PharmacyResponse>> update(
            @PathVariable UUID id,
            @RequestBody PharmacyRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Pharmacy updated successfully",
                        pharmacyService.update(id, request),
                        HttpStatus.OK.value()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable UUID id) {
        pharmacyService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.success("Pharmacy deleted successfully", null, HttpStatus.OK.value())
        );
    }
}
