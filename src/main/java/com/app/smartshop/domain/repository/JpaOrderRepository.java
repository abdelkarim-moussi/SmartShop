package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.entity.Client;
import com.app.smartshop.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface JpaOrderRepository extends JpaRepository<Order,String> {
    long countByPromotionCode(String code);
    List<Order> findAllByClient(Client client);
    @Query("SELECT SUM (o.total) FROM Order o WHERE o.client = :client AND o.status = com.app.smartshop.domain.enums.OrderStatus.CONFIRMED")
    Optional<BigDecimal> sumTotalConfirmedOrdersByClient(Client client);
}
