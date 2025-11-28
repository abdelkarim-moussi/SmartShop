package com.app.smartshop.infrastructure.persistence.repository;
import com.app.smartshop.domain.model.search.ProductCriteria;
import com.app.smartshop.domain.model.Product;
import com.app.smartshop.domain.repository.IProductRepository;
import com.app.smartshop.infrastructure.controller.dto.DomainPageRequest;
import com.app.smartshop.infrastructure.controller.dto.Page;
import com.app.smartshop.infrastructure.controller.mapper.ProductModelEntityMapper;
import com.app.smartshop.infrastructure.persistence.entity.ProductEntity;
import com.app.smartshop.infrastructure.persistence.repository.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<Product> findAll(DomainPageRequest domainPageRequest, ProductCriteria filters) {
        Specification<ProductEntity> specification = ProductSpecification.byFilters(filters);

        Pageable pageable = PageRequest.of(domainPageRequest.getPage(),domainPageRequest.getSize());
        org.springframework.data.domain.Page<ProductEntity> jpaPage = productRepository.findAll(specification,pageable);
        return new Page<>(
                jpaPage.getContent().stream().map(mapper::toModel).toList(),
                jpaPage.getTotalElements(),
                jpaPage.getTotalPages()
        );
    }
}
