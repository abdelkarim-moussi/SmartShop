package com.app.smartshop.application.dto.payment;

import com.app.smartshop.domain.enums.PaymentStatus;
import com.app.smartshop.domain.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    @NotNull
    private String orderId;
    private byte paymentNumber;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private LocalDate collectionDate;
    private LocalDate dueDate;
    private String reference;
    private String bankName;
    private PaymentStatus status;
    private PaymentType type;
    private BigDecimal remaining;
}
