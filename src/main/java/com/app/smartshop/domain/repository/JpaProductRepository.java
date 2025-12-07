package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<Product,String>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String name);
    boolean existsByName(String name);
    boolean existsById(String id);
}
