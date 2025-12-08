package com.app.smartshop.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "unit_price",nullable = false)
    private BigDecimal unitPrice;
    @Column(name="stock",nullable = false)
    private int stock;
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    public void decrementStock(int demandedQuantity){
        if(demandedQuantity > 0){
            this.stock -= demandedQuantity;
        }
    }

    public void incrementStock(int returnedQuantity){
        if(returnedQuantity > 0){
            this.stock += returnedQuantity;
        }
    }


}
