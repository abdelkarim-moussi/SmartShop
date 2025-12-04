package com.app.smartshop.application.mapper;

import com.app.smartshop.application.dto.ClientRequestDTO;
import com.app.smartshop.application.dto.ClientResponseDTO;
import com.app.smartshop.domain.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientModelDTOMapper {
    Client toDomainModel(ClientRequestDTO dto);
    ClientResponseDTO toResponseDTO(Client domainModel);
}
