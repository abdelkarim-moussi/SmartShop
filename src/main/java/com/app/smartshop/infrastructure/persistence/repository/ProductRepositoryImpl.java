package com.app.smartshop.infrastructure.persistence.repository;

import com.app.smartshop.application.dto.client.ClientFilters;
import com.app.smartshop.domain.model.Product;
import com.app.smartshop.domain.repository.IProductRepository;
import com.app.smartshop.domain.repository.specification.DomainPageRequest;
import com.app.smartshop.domain.repository.specification.Page;
import com.app.smartshop.infrastructure.mapper.ProductModelEntityMapper;
import com.app.smartshop.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements IProductRepository {

    private final JpaProductRepository productRepository;
    private final ProductModelEntityMapper mapper;


    @Override
    public Product findProductByName(String name) {
        return null;
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public boolean existsById(String id) {
        return productRepository.existsById(id);
    }

    @Override
    public Product save(Product product) {
        ProductEntity savedProduct = productRepository.save(mapper.toEntity(product));
        return mapper.toModel(savedProduct);
    }

    @Override
    public Product update(Product product) {
        ProductEntity updated = productRepository.save(mapper.toEntity(product));
        return mapper.toModel(updated);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(String id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        return product.map(mapper::toModel);
    }

    @Override
    public Page<Product> findAll(DomainPageRequest domainPageRequest, ClientFilters clientFilters) {
        return null;
    }
}
