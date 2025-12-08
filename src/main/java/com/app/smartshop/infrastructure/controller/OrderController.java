package com.app.smartshop.infrastructure.controller;

import com.app.smartshop.application.dto.OrderRequestDTO;
import com.app.smartshop.application.dto.OrderResponseDTO;
import com.app.smartshop.application.dto.PaymentRequestDTO;
import com.app.smartshop.application.dto.PaymentResponseDTO;
import com.app.smartshop.application.service.IOrderService;
import com.app.smartshop.application.service.IPaymentService;
import com.app.smartshop.application.service.PayOrder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final PayOrder payOrder;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createNewOrder(@RequestBody @Valid OrderRequestDTO request){
        OrderResponseDTO response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponseDTO> payOrder(@RequestBody @Valid PaymentRequestDTO request){
        PaymentResponseDTO response = payOrder.payOrder(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    private ResponseEntity<OrderResponseDTO> confirmOrder(@RequestParam (value = "id") String id){
        OrderResponseDTO response = orderService.confirmOrder(id);
        return ResponseEntity.ok(response);
    }
}
