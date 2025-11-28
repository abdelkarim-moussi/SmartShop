package com.app.smartshop.domain.model;

import com.app.smartshop.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tva;
    private BigDecimal total;
    private String promotionCode;
    private OrderStatus status;
    private BigDecimal restAmount;
    private Client client;
    private List<OrderItem> articlesList;

    public BigDecimal calculateSubTotal(){
        return articlesList.stream()
                .map(o -> o.getUnitPrice().multiply(BigDecimal.valueOf(o.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

}
