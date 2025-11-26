package com.app.smartshop.infrastructure.persistence.entity;

import com.app.smartshop.domain.enums.LoyaltyLevel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clients")
public class ClientEntity {
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
}
