package com.app.smartshop.infrastructure.persistence.repository;

import com.app.smartshop.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<ProductEntity,String> {
    Optional<ProductEntity> findByName(String name);
    boolean existsByName(String name);
}
