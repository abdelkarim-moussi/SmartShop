package com.app.smartshop.application.mapper;

import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.domain.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientRequestDTO dto);
    ClientResponseDTO toResponseDTO(Client domainModel);
}
