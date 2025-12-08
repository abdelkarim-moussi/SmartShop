package com.app.smartshop.application.dto;

import com.app.smartshop.domain.enums.PaymentStatus;
import com.app.smartshop.domain.enums.PaymentType;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponseDTO {
    private String id;
    private String orderId;
    private byte paymentNumber;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private LocalDate collectionDate;
    private LocalDate dueDate;
    private String reference;
    private String bankName;
    private String receiptNumber;
    private String checkNumber;
    private PaymentStatus status;
    private PaymentType type;
    private BigDecimal remaining;
}
