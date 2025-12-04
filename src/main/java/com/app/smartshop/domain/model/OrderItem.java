package com.app.smartshop.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private String id;
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalLine;

    public BigDecimal calculateTotalLine(){
        if(unitPrice == null || quantity <= 0){
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    };
}
