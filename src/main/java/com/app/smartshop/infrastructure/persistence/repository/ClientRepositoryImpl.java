package com.app.smartshop.infrastructure.persistence.repository;

import com.app.smartshop.domain.model.Client;
import com.app.smartshop.domain.repository.IClientRepository;
import com.app.smartshop.infrastructure.mapper.ClientModelEntityMapper;
import com.app.smartshop.infrastructure.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClientRepository {
    private final JpaClientRepository jpaClientRepository;
    private final ClientModelEntityMapper clientModelEntityMapper;

    @Override
    public boolean existsByEmail(String email) {
        return jpaClientRepository.existsByEmail(email);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return jpaClientRepository.findByEmail(email).map(clientModelEntityMapper::toDomainModel);
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = clientModelEntityMapper.toEntity(client);
        return clientModelEntityMapper.toDomainModel(jpaClientRepository.save(entity));
    }

    @Override
    public void delete(Client client) {
        ClientEntity entity = clientModelEntityMapper.toEntity(client);
        jpaClientRepository.delete(entity);
    }

    @Override
    public Optional<Client> findById(String id) {
        return jpaClientRepository.findById(id).map(clientModelEntityMapper::toDomainModel);
    }

    @Override
    public List<Client> findAll() {
        return jpaClientRepository.findAll()
                .stream()
                .map(clientModelEntityMapper::toDomainModel).toList();
    }
}
