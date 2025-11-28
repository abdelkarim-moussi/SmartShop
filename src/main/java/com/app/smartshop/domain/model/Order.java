package com.app.smartshop.domain.model;

import com.app.smartshop.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

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

    private int percentDiscountBasedOnLoyalty(){
        return switch(this.client.getLoyaltyLevel()){
            case BASIC -> 0;
            case SILVER -> subtotal.compareTo(BigDecimal.valueOf(500)) > 0 ? 5 : 0;
            case GOLD -> subtotal.compareTo(BigDecimal.valueOf(800)) > 0 ? 10 : 0;
            case PLATINUM -> subtotal.compareTo(BigDecimal.valueOf(1200)) > 0 ? 15 : 0;
        };
    }

    private BigDecimal calculateDiscount(int discountPercent){
        if(discountPercent <= 0){
            return BigDecimal.ZERO;
        }
        return subtotal.multiply(BigDecimal.valueOf(discountPercent)).
                divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
    };


    public BigDecimal calculateTotalDiscount(int i){
        BigDecimal total = calculateDiscount(this.percentDiscountBasedOnLoyalty());
        if(promotionCode != null && !promotionCode.isEmpty()){
            BigDecimal promotionDiscount = calculateTotalDiscount(5);
            total = total.add(promotionDiscount);
        }
        return total;
    }
}
