package com.app.smartshop.infrastructure.controller;

import com.app.smartshop.application.dto.OrderRequestDTO;
import com.app.smartshop.application.dto.OrderResponseDTO;
import com.app.smartshop.application.service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders/")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createNewOrder(@RequestBody @Valid OrderRequestDTO request){
        OrderResponseDTO response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }
}
