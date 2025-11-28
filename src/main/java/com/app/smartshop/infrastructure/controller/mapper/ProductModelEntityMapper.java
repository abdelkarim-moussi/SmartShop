package com.app.smartshop.infrastructure.mapper;

import com.app.smartshop.domain.model.Product;
import com.app.smartshop.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductModelEntityMapper {
    Product toModel(ProductEntity entity);
    ProductEntity toEntity(Product product);
}
