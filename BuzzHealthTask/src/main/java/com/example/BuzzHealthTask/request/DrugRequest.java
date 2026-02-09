package com.example.BuzzHealthTask.request;


import jakarta.validation.constraints.NotBlank;

public record DrugRequest(
        @NotBlank String name,
        @NotBlank String ndc,
        String strength,
        String form
) {}
