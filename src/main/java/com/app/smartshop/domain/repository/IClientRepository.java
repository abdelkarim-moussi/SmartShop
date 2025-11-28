package com.app.smartshop.domain.repository;

import com.app.smartshop.application.dto.client.ClientFilters;
import com.app.smartshop.domain.model.Client;

import java.util.Optional;

public interface IClientRepository extends GenericRepository<Client,String, ClientFilters> {
    boolean existsByEmail(String email);
    Optional<Client> findByEmail(String email);
}
