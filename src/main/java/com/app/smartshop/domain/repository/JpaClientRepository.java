package com.app.smartshop.domain.repository;
import com.app.smartshop.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaClientRepository extends JpaRepository<Client,String>, JpaSpecificationExecutor<Client> {
    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);
}
