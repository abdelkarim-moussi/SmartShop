package com.app.smartshop.application.mapper;

import com.app.smartshop.domain.model.Client;
import com.app.smartshop.infrastructure.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientModelEntityMapper {
    Client toDomainModel(ClientEntity entity);
    ClientEntity toEntity(Client client);
}
