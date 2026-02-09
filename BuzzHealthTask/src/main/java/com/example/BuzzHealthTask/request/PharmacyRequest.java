package com.example.BuzzHealthTask.request;

public record PharmacyRequest(
        String npi,
        String name,
        String address,
        String city,
        String state,
        String zip
) {}
