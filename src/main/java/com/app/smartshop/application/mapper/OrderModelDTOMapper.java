package com.app.smartshop.application.mapper;

import com.app.smartshop.application.dto.OrderRequestDTO;
import com.app.smartshop.application.dto.OrderResponseDTO;
import com.app.smartshop.domain.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderModelDTOMapper {
    Order toDomainModel(OrderRequestDTO dto);
    OrderResponseDTO toResponseDto(Order model);
}
