package com.app.smartshop.application.service;
import com.app.smartshop.application.dto.OrderRequestDTO;
import com.app.smartshop.application.dto.OrderResponseDTO;
import com.app.smartshop.domain.model.Order;
import com.app.smartshop.domain.model.search.OrderCriteria;
import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.application.dto.Page;

public interface IOrderService {
    OrderResponseDTO createOrder(OrderRequestDTO order);
    OrderResponseDTO updateOrder(String id,OrderRequestDTO order);
    OrderResponseDTO findOrderById(String id);
    void deleteOrderById(String id);
    Page<OrderResponseDTO> findAllOrders(DomainPageRequest domainPageRequest, OrderCriteria filters);
}
