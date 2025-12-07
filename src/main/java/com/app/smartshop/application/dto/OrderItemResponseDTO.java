package com.app.smartshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private String id;
    private String productId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalLine;
}
