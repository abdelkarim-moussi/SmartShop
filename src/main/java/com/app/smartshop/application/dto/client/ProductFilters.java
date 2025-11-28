package com.app.smartshop.application.dto.client;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductFilters {
    String name;
    BigDecimal unitPrice;
}
