package com.app.smartshop.infrastructure.controller.mapper;
import com.app.smartshop.infrastructure.controller.dto.ProductRequestDTO;
import com.app.smartshop.infrastructure.controller.dto.ProductResponseDTO;
import com.app.smartshop.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductModelDTOMapper {
    Product toDomainModel(ProductRequestDTO dto);
    ProductResponseDTO toResponseDTO(Product product);
}
