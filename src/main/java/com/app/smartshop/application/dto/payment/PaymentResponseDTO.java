package com.app.smartshop.application.dto.payment;

import com.app.smartshop.domain.enums.PaymentStatus;
import com.app.smartshop.domain.enums.PaymentType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private BigDecimal orderTotal;
    private byte paymentNumber;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private PaymentStatus status;
    private PaymentType type;
    private BigDecimal remaining;
    private String reference;

    private Object paymentDetails;
}
