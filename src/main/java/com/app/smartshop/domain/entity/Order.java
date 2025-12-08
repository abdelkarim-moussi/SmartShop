package com.app.smartshop.domain.entity;

import com.app.smartshop.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "date",nullable = false)
    private LocalDateTime date;
    @Column(name = "sub_total",nullable = false)
    private BigDecimal subtotal;
    @Column(nullable = false)
    private BigDecimal discount;
    @Column(nullable = false)
    private BigDecimal tva;
    @Column(nullable = false)
    private BigDecimal total;
    private String promotionCode;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal restAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> itemsList = new ArrayList<>();

    public void calculateAmounts(){
        this.subtotal = calculateSubTotal();
        this.discount = calculateTotalDiscount();

        BigDecimal amountAfterDiscount = subtotal.subtract(this.discount);
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

    public void addItem(OrderItem item){
        if(this.itemsList == null){
            this.itemsList = new ArrayList<>();
        }
        item.setOrder(this);
        this.itemsList.add(item);
    }


}
