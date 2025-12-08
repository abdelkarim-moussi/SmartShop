package com.app.smartshop.application.service;
import com.app.smartshop.application.dto.OrderRequestDTO;
import com.app.smartshop.application.dto.OrderResponseDTO;
import com.app.smartshop.domain.entity.search.OrderCriteria;
import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.application.dto.Page;

public interface IOrderService {
    OrderResponseDTO createOrder(OrderRequestDTO order);
    OrderResponseDTO confirmOrder(String id);
    OrderResponseDTO cancelOrder(String id);
}
