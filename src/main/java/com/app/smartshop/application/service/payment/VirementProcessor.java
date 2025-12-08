package com.app.smartshop.application.service.payment;

import com.app.smartshop.application.dto.PaymentRequestDTO;
import com.app.smartshop.domain.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("VIREMENT")
public class VirementProcessor implements PaymentProcessor {
    @Override
    public void validate(PaymentRequestDTO payment) {
        if(payment.getReference() == null || payment.getReference().isEmpty()){
            throw new IllegalArgumentException("payment by virement must have a reference");
        }
        if(payment.getBankName() == null || payment.getBankName().isEmpty()){
            throw new IllegalArgumentException("payment by virement must have a bank name");
        }
    }

    @Override
    public PaymentStatus determineInitialStatus(PaymentRequestDTO payment) {
        if(payment.getDueDate() != null && payment.getDueDate().isAfter(LocalDate.now())){
            return PaymentStatus.EN_ATTENTE;
        }
        return PaymentStatus.ENCAISSE;
    }
}
