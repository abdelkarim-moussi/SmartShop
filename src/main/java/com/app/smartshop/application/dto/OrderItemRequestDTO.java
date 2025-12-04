package com.app.smartshop.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequestDTO {
    private String productId;
    private int quantity;
}
