package com.app.smartshop.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    };
}
