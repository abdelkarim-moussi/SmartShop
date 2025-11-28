package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.model.search.ClientCriteria;
import com.app.smartshop.domain.model.Client;

import java.util.Optional;

public interface IClientRepository extends GenericRepository<Client,String, ClientCriteria> {
    boolean existsByEmail(String email);
    Optional<Client> findByEmail(String email);
}
