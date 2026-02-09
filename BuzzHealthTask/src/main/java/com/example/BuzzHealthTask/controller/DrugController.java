package com.example.BuzzHealthTask.controller;

import com.example.BuzzHealthTask.request.DrugRequest;
//import com.example.BuzzHealthTask.response.BaseResponse;
import com.example.BuzzHealthTask.request.DrugUpdateRequest;
import com.example.BuzzHealthTask.response.BaseResponse;
import com.example.BuzzHealthTask.response.DrugResponse;
import com.example.BuzzHealthTask.service.DrugService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @PostMapping
    public ResponseEntity<BaseResponse<DrugResponse>> create(
            @Valid @RequestBody DrugRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(
                        "Drug created successfully",
                        drugService.create(request),
                        HttpStatus.CREATED.value()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<DrugResponse>> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Drug fetched successfully",
                        drugService.getById(id),
                        HttpStatus.OK.value()
                )
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<DrugResponse>>> getAll() {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Drugs fetched successfully",
                        drugService.getAll(),
                        HttpStatus.OK.value()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<DrugResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody DrugUpdateRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        "Drug updated successfully",
                        drugService.update(id, request),
                        HttpStatus.OK.value()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> delete(
            @PathVariable UUID id
    ) {
        drugService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.success("Drug deleted successfully", null, HttpStatus.OK.value())
        );
    }
}
