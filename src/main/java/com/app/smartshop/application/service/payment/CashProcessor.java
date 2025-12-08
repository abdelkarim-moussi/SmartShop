package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.PaymentRequestDTO;
import com.app.smartshop.domain.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("ESPECES")
public class CashProcessor implements PaymentProcessor{
    private final BigDecimal LEGAL_LIMIT = BigDecimal.valueOf(20000);

    @Override
    public void validate(PaymentRequestDTO payment) {
        if(payment.getAmount().compareTo(LEGAL_LIMIT) > 0){
            throw new IllegalArgumentException("Cash payment exceeds the legal limit of 20,000 DH.");
        }

        if(payment.getReference() == null || payment.getReference().isEmpty()){
            throw new IllegalArgumentException("Cash payment must have a receipt number.");
        }
    }

    @Override
    public PaymentStatus determineInitialStatus(PaymentRequestDTO payment) {
        return PaymentStatus.ENCAISSE;
    }
}
