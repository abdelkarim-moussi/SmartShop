package com.app.smartshop.infrastructure.persistence.repository;
import com.app.smartshop.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaClientRepository extends JpaRepository<ClientEntity,String> {
    Optional<ClientEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
