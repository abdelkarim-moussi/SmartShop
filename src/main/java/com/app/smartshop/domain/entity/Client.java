package com.app.smartshop.domain.entity;

import com.app.smartshop.domain.enums.LoyaltyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "loyalty_level",nullable = false)
    private LoyaltyLevel loyaltyLevel;
    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();
}
