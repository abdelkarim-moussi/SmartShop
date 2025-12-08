package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order,String> {
    long countByPromotionCode(String code);
}
