package com.app.smartshop.infrastructure.mapper;
import com.app.smartshop.infrastructure.client.ProductRequestDTO;
import com.app.smartshop.infrastructure.client.ProductResponseDTO;
import com.app.smartshop.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductModelDTOMapper {
    Product toDomainModel(ProductRequestDTO dto);
    ProductResponseDTO toResponseDTO(Product product);
}
