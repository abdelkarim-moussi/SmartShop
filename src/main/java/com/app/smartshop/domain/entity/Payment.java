package com.app.smartshop.domain.entity;

import com.app.smartshop.domain.enums.PaymentStatus;
import com.app.smartshop.domain.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String orderId;
    private byte paymentNumber;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private LocalDate collectionDate;
    private LocalDate dueDate;
    private String reference;
    private String bankName;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    private BigDecimal remaining;
}
