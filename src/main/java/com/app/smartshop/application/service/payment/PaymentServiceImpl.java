package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.*;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.PaymentMapper;
import com.app.smartshop.domain.entity.Order;
import com.app.smartshop.domain.entity.Payment;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import com.app.smartshop.domain.repository.JpaPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService{
    private final Map<String,PaymentProcessor> processors;
    private final JpaPaymentRepository paymentRepository;
    private final JpaOrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public Payment makeAndValidatePayment(PaymentRequestDTO payment){

        Order order = orderRepository.findById(payment.getOrderId()).orElseThrow(
                ()-> new DataNotExistException("there is no order with this id")
        );

        String processorKey = payment.getType().toString();

        PaymentProcessor processor = processors.get(processorKey);

        if(processor == null){
            throw new InvalidParameterException("unsupported payment method type: "+processorKey);
        }
        processor.validate(payment);
        payment.setStatus(processor.determineInitialStatus(payment));

        return paymentMapper.toEntity(payment);
    }

}
