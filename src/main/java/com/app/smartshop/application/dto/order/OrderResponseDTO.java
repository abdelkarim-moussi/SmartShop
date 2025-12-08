package com.app.smartshop.application.dto.order;

import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderResponseDTO {
    private String id;
    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tva;
    private BigDecimal total;
    private OrderStatus status;
    private BigDecimal restAmount;
    private ClientResponseDTO client;
    private List<OrderItemResponseDTO> itemsList;
}
