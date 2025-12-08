package com.app.smartshop.application.dto;

import com.app.smartshop.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientOrdersResponse {
    private String id;
    private LocalDateTime date;
    private BigDecimal total;
    private OrderStatus status;
}
