package com.app.smartshop.application.service;
import com.app.smartshop.application.dto.OrderRequestDTO;
import com.app.smartshop.application.dto.OrderResponseDTO;

public interface IOrderService {
    OrderResponseDTO createOrder(OrderRequestDTO order);
    OrderResponseDTO confirmOrder(String id);
    OrderResponseDTO cancelOrder(String id);
}
