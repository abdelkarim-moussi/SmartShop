package com.app.smartshop.application.service.payment;

import com.app.smartshop.application.dto.payment.PaymentRequestDTO;
import com.app.smartshop.domain.enums.PaymentStatus;

public interface PaymentProcessor {
    void validate(PaymentRequestDTO payment);
    PaymentStatus determineInitialStatus(PaymentRequestDTO payment);
}
