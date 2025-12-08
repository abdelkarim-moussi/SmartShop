package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<Payment,String > {
}
