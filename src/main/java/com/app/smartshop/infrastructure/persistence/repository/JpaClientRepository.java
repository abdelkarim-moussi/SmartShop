package com.app.smartshop.infrastructure.persistence.repository;

import com.app.smartshop.domain.model.Client;
import com.app.smartshop.domain.repository.IClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAClientRepository extends JpaRepository<Client,String>, IClientRepository {
}
