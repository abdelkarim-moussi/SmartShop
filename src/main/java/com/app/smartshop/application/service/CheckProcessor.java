package com.app.smartshop.application.service;

import com.app.smartshop.domain.entity.Payment;
import com.app.smartshop.domain.enums.PaymentStatus;

public class ChequeProcessor implements PaymentProcessor{
    @Override
    public void validate(Payment payment) {
        
    }

    @Override
    public PaymentStatus determineInitialStatus(Payment payment) {
        return null;
    }
}
