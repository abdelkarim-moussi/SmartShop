package com.app.smartshop.application.mapper;

import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.domain.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientModelDTOMapper {
    Client toDomainModel(ClientRequestDTO dto);
    ClientResponseDTO toResponseDTO(Client domainModel);
}
