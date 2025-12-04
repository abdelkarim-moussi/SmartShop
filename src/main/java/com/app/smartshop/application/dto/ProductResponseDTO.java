package com.app.smartshop.application.dto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private String id;
    private String name;
    private BigDecimal unitPrice;
    private int stock;
}
