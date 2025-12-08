package com.app.smartshop.application.service.payment;

import com.app.smartshop.application.dto.payment.PaymentRequestDTO;
import com.app.smartshop.domain.enums.PaymentStatus;
import org.springframework.stereotype.Service;

@Service("CHECK")
public class CheckProcessor implements PaymentProcessor {
    @Override
    public void validate(PaymentRequestDTO payment) {
        if(payment.getReference() == null || payment.getReference().isEmpty()){
            throw new IllegalArgumentException("payment by check must have check number");
        }
        if(payment.getBankName() == null || payment.getBankName().isEmpty()){
            throw new IllegalArgumentException("payment by check must have bank name");
        }
        if(payment.getDueDate() == null){
            throw new IllegalArgumentException("payment by check must have due date");
        }
    }

    @Override
    public PaymentStatus determineInitialStatus(PaymentRequestDTO payment) {
        return PaymentStatus.EN_ATTENTE;
    }
}
