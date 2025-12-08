package com.app.smartshop.application.mapper;

import com.app.smartshop.application.dto.payment.PaymentRequestDTO;
import com.app.smartshop.application.dto.payment.PaymentResponseDTO;
import com.app.smartshop.domain.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponseDTO toDTO(Payment payment);
    Payment toEntity(PaymentRequestDTO requestDTO);
}
