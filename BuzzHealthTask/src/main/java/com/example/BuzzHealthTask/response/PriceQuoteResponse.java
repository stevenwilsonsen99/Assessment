package com.example.BuzzHealthTask.response;

import java.math.BigDecimal;

public record PriceQuoteResponse(
        BigDecimal priceBefore,
        BigDecimal priceAfter
) {}

