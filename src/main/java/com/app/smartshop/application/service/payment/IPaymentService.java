package com.app.smartshop.application.service.payment;

import com.app.smartshop.application.dto.PaymentRequestDTO;
import com.app.smartshop.domain.entity.Payment;

public interface IPaymentService {
    Payment makeAndValidatePayment(PaymentRequestDTO payment);
}
