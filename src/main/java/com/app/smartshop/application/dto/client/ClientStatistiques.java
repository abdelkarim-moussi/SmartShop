package com.app.smartshop.application.dto.client;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientStatistiques {
    private int totalNumberOfOrders;
    private BigDecimal confirmedOrdersTotalAmounts;
    private String firstOrderDate;
    private String lastOrderDate;
}
