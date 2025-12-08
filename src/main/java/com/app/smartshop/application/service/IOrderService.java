package com.app.smartshop.application.service;
import com.app.smartshop.application.dto.order.OrderRequestDTO;
import com.app.smartshop.application.dto.order.OrderResponseDTO;

public interface IOrderService {
    OrderResponseDTO createOrder(OrderRequestDTO order);
    OrderResponseDTO confirmOrder(String id);
    OrderResponseDTO cancelOrder(String id);
}
