package com.app.smartshop.domain.model.search;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductCriteria {
    String name;
    BigDecimal unitPrice;
}
