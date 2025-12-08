package com.app.smartshop.application.mapper;
import com.app.smartshop.application.dto.ProductRequestDTO;
import com.app.smartshop.application.dto.ProductResponseDTO;
import com.app.smartshop.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDTO dto);
    ProductResponseDTO toResponseDTO(Product product);
}
