package com.example.BuzzHealthTask.service;

import com.example.BuzzHealthTask.request.DrugRequest;
import com.example.BuzzHealthTask.request.DrugUpdateRequest;
import com.example.BuzzHealthTask.response.DrugResponse;

import java.util.List;
import java.util.UUID;

public interface DrugService {

    DrugResponse create(DrugRequest request);

    DrugResponse getById(UUID id);

    List<DrugResponse> getAll();

    DrugResponse update(UUID id, DrugUpdateRequest request);

    void delete(UUID id);
}

