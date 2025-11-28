package com.app.smartshop.domain.model.search;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductFilters {
    String name;
    BigDecimal unitPrice;
}
