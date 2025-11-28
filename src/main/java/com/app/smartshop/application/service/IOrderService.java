package com.app.smartshop.application.service;
import com.app.smartshop.domain.model.Order;
import com.app.smartshop.domain.model.search.OrderCriteria;
import com.app.smartshop.infrastructure.controller.dto.DomainPageRequest;
import com.app.smartshop.infrastructure.controller.dto.Page;

public interface IOrderService {
    Order createOrder(Order order);
    Order updateOrder(String id,Order order);
    Order findOrderById(String id);
    void deleteOrderById(String id);
    Page<Order> findAllOrders(DomainPageRequest domainPageRequest, OrderCriteria filters);
}
