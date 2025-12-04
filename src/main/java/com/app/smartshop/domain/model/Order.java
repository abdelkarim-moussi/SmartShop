package com.app.smartshop.domain.model;

import com.app.smartshop.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private List<OrderItem> itemsList = new ArrayList<>();


    public void calculateAmounts(){
        BigDecimal subtotal = calculateSubTotal();
        BigDecimal totalDiscount = calculateTotalDiscount();

        BigDecimal amountAfterDiscount = subtotal.subtract(totalDiscount);
        this.tva = amountAfterDiscount.multiply(BigDecimal.valueOf(0.20))
                .setScale(2,RoundingMode.HALF_UP);

        this.total = amountAfterDiscount.add(this.tva).setScale(2,RoundingMode.HALF_UP);
        this.restAmount = this.total;
    }

    private BigDecimal calculateSubTotal(){
        return itemsList.stream()
                .map(item -> item.getUnitPrice().
                        multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add)
                .setScale(2,RoundingMode.HALF_UP);
    }

    private BigDecimal calculateLoyaltyDiscount() {
        int percent = switch (client.getLoyaltyLevel()) {
            case BASIC -> 0;
            case SILVER -> subtotal.compareTo(BigDecimal.valueOf(500)) >= 0 ? 5 : 0;
            case GOLD -> subtotal.compareTo(BigDecimal.valueOf(800)) >= 0 ? 10 : 0;
            case PLATINUM -> subtotal.compareTo(BigDecimal.valueOf(1200)) >= 0 ? 15 : 0;
        };

        return applyPercent(subtotal, percent);
    }

    private BigDecimal calculatePromoDiscount(){
        if(promotionCode == null || promotionCode.isEmpty()){
            return BigDecimal.ZERO;
        }
        return applyPercent(subtotal,5).setScale(2,RoundingMode.HALF_UP);
    }

    private BigDecimal applyPercent(BigDecimal amount, int percent){
        if(percent <= 0) return BigDecimal.ZERO;

        return amount.multiply(BigDecimal.valueOf(percent))
                .divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotalDiscount(){
        BigDecimal loyaltyDiscount = this.calculateLoyaltyDiscount();
        BigDecimal promoDiscount = this.calculatePromoDiscount();

        return loyaltyDiscount.add(promoDiscount)
                .setScale(2,RoundingMode.HALF_UP);
    };

    public void rejectOrder(){
        this.status = OrderStatus.REJECTED;
    }

    public boolean isCompletelyPaid(){
        return restAmount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean canBeConfirmed(){
        return status == OrderStatus.PENDING && isCompletelyPaid();
    }


}
