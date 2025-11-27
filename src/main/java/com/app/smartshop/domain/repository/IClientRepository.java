package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.model.Client;

import java.util.Optional;

public interface IClientRepository extends GenericRepository<Client,String> {
    public boolean existsByEmail(String email);
    public Optional<Client> findByEmail(String email);
}
