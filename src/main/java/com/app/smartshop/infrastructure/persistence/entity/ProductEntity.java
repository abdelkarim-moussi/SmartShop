package com.app.smartshop.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "unit_price",nullable = false)
    private BigDecimal unitPrice;
    @Column(name="stock",nullable = false)
    private int stock;
}
