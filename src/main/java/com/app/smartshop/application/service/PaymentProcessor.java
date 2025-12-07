package com.app.smartshop.application.service;

import com.app.smartshop.domain.entity.Payment;
import com.app.smartshop.domain.enums.PaymentStatus;

public interface PaymentProcessor {
    void validate(Payment payment);
    PaymentStatus determineInitialStatus(Payment payment);
}
