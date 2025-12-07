package com.app.smartshop.application.service;

import com.app.smartshop.domain.entity.Payment;
import com.app.smartshop.domain.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("CACH_PROCESSOR")
public class CashProcessor implements PaymentProcessor{
    private final BigDecimal LEGAL_LIMIT = BigDecimal.valueOf(20000);

    @Override
    public void validate(Payment payment) {
        if(payment.getAmount().compareTo(LEGAL_LIMIT) > 0){
            throw new IllegalArgumentException("Cash payment exceeds the legal limit of 20,000 DH.");
        }
    }

    @Override
    public PaymentStatus determineInitialStatus(Payment payment) {
        return PaymentStatus.ENCAISSÉ;
    }
}
