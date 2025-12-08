package com.app.smartshop.application.dto.order;

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
