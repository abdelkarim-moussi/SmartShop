package com.app.smartshop.infrastructure.persistence.repository;

import com.app.smartshop.application.dto.client.Filters;
import com.app.smartshop.domain.model.Client;
import com.app.smartshop.domain.repository.IClientRepository;
import com.app.smartshop.domain.repository.specification.Page;
import com.app.smartshop.domain.repository.specification.DomainPageRequest;
import com.app.smartshop.infrastructure.mapper.ClientModelEntityMapper;
import com.app.smartshop.infrastructure.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
    public Client update(Client client) {
        ClientEntity updatedClient = jpaClientRepository.save(clientModelEntityMapper.toEntity(client));
        return clientModelEntityMapper.toDomainModel(updatedClient);
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
    public Page<Client> findAll(DomainPageRequest pageRequest, Filters filters) {
        Pageable pageable = PageRequest.of(pageRequest.getPage(),pageRequest.getSize());
        org.springframework.data.domain.Page<ClientEntity> jpaPage = jpaClientRepository.findAll(pageable);

        return new Page<>(
                jpaPage.getContent().stream().map(clientModelEntityMapper::toDomainModel).toList(),
                jpaPage.getTotalElements(),
                jpaPage.getTotalPages()
        );
    }
}
