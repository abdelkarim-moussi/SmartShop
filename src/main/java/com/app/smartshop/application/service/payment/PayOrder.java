package com.app.smartshop.application.service.payment;

import com.app.smartshop.application.dto.payment.CheckPaymentDetails;
import com.app.smartshop.application.dto.payment.PaymentRequestDTO;
import com.app.smartshop.application.dto.payment.PaymentResponseDTO;
import com.app.smartshop.application.dto.payment.VirementPaymentDetails;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.mapper.PaymentMapper;
import com.app.smartshop.domain.entity.Order;
import com.app.smartshop.domain.entity.Payment;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import com.app.smartshop.domain.repository.JpaPaymentRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PayOrder {
    private final IPaymentService paymentService;
    private final JpaOrderRepository orderRepository;
    private final JpaPaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentResponseDTO payOrder(PaymentRequestDTO paymentRequest){
        Order order = orderRepository.findById(paymentRequest.getOrderId()).orElseThrow(
                ()-> new DataNotExistException("there is no order with this id: "+paymentRequest.getOrderId())
        );

        Payment payment = paymentService.makeAndValidatePayment(paymentRequest);

        order.setRestAmount(order.getRestAmount().subtract(payment.getAmount()));
        Order updatedOrder = orderRepository.save(order);

        payment.setRemaining(updatedOrder.getRestAmount());
        Payment savedPayment = paymentRepository.save(payment);

        PaymentResponseDTO paymentResponse = paymentMapper.toDTO(savedPayment);
        paymentResponse.setOrderTotal(updatedOrder.getTotal());
        paymentResponse.setPaymentDetails(mapDetailsToResponseDTO(savedPayment));

        return paymentResponse;
    }

    private Object mapDetailsToResponseDTO(Payment payment) {
        return switch (payment.getType()) {
            case CHECK -> new CheckPaymentDetails(
                    payment.getBankName(),
                    payment.getDueDate()
            );

            case VIREMENT -> new VirementPaymentDetails(
                    payment.getBankName()
            );
            case ESPECES -> null;
        };
    }
}
