package com.example.BuzzHealthTask.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record DrugResponse(
        UUID id,
        String name,
        String ndc,
        String strength,
        String form,
        LocalDateTime createdAt
) {}

