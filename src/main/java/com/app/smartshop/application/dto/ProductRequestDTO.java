package com.app.smartshop.application.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "product name is required")
    private String name;
    @Min(1)
    private BigDecimal unitPrice;
    @Min(1)
    private int stock;
}
