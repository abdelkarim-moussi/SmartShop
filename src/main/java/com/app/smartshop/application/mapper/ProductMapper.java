package com.app.smartshop.application.mapper;
import com.app.smartshop.application.dto.product.ProductRequestDTO;
import com.app.smartshop.application.dto.product.ProductResponseDTO;
import com.app.smartshop.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDTO dto);
    ProductResponseDTO toResponseDTO(Product product);
}
