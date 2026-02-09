package com.example.BuzzHealthTask.request;

public record DrugUpdateRequest(
        String name,
        String ndc,
        String strength,
        String form
) {}
