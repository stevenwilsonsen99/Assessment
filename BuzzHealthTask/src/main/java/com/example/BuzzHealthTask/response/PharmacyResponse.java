package com.example.BuzzHealthTask.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PharmacyResponse(
        UUID id,
        String npi,
        String name,
        String city,
        String state,
        LocalDateTime createdAt
) {}

