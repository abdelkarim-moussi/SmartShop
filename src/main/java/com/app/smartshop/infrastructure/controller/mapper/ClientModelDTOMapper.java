package com.app.smartshop.infrastructure.controller.mapper;

import com.app.smartshop.infrastructure.controller.dto.ClientRequestDTO;
import com.app.smartshop.infrastructure.controller.dto.ClientResponseDTO;
import com.app.smartshop.domain.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientModelDTOMapper {
    Client toDomainModel(ClientRequestDTO dto);
    ClientResponseDTO toResponseDTO(Client domainModel);
}
