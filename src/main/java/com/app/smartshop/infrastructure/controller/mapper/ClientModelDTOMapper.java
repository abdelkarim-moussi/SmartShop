package com.app.smartshop.infrastructure.mapper;

import com.app.smartshop.infrastructure.client.ClientRequestDTO;
import com.app.smartshop.infrastructure.client.ClientResponseDTO;
import com.app.smartshop.domain.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientModelDTOMapper {
    Client toDomainModel(ClientRequestDTO dto);
    ClientResponseDTO toResponseDTO(Client domainModel);
}
