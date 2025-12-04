package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.model.Order;
import com.app.smartshop.domain.model.search.OrderCriteria;

public interface IOrderRepository extends GenericRepository<Order,String, OrderCriteria> {
}
