package com.app.smartshop.application.mapper;
import com.app.smartshop.application.dto.client.ProductRequestDTO;
import com.app.smartshop.application.dto.client.ProductResponseDTO;
import com.app.smartshop.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductModelDTOMapper {
    Product toDomainModel(ProductRequestDTO dto);
    ProductResponseDTO toResponseDTO(Product product);
}
