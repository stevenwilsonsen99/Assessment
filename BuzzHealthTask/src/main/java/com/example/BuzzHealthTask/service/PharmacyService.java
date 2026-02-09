package com.example.BuzzHealthTask.service;

import com.example.BuzzHealthTask.request.PharmacyRequest;
import com.example.BuzzHealthTask.response.PharmacyResponse;

import java.util.List;
import java.util.UUID;

public interface PharmacyService {

    PharmacyResponse create(PharmacyRequest request);

    PharmacyResponse getById(UUID id);

    List<PharmacyResponse> getAll();

    PharmacyResponse update(UUID id, PharmacyRequest request);

    void delete(UUID id);
}

