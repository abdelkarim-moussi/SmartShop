package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPaymentRepository extends JpaRepository<Payment,String > {
    List<Payment> findAllByOrderId(String orderId);
}
