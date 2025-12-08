package com.app.smartshop.application.mapper;

import com.app.smartshop.application.dto.client.ClientOrdersResponse;
import com.app.smartshop.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientOrderMapper {
    ClientOrdersResponse toResponse(Order order);
}
