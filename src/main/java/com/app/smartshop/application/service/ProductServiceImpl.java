package com.app.smartshop.application.service;

import com.app.smartshop.domain.entity.search.ProductCriteria;
import com.app.smartshop.application.dto.product.ProductRequestDTO;
import com.app.smartshop.application.dto.product.ProductResponseDTO;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.exception.ProductExistByNameException;
import com.app.smartshop.application.mapper.ProductMapper;
import com.app.smartshop.domain.entity.Product;
import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.domain.repository.JpaProductRepository;
import com.app.smartshop.domain.repository.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements IProductService{
    private final JpaProductRepository productRepository;
    private final ProductMapper mapper;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO product) {
        if(product == null){
            throw new InvalidParameterException("product data can not be null");
        }
        boolean exist = productRepository.existsByName(product.getName());

        if(exist){
            throw new ProductExistByNameException("there is already a product with this name: "+product.getName()+"\ntry to increase the stock instead ");
        }

        Product savedProduct = productRepository.save(mapper.toEntity(product));

        return mapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO product) {
        if(id == null || id.trim().isEmpty()){
            throw new InvalidParameterException("id can not be null or empty");
        }

        Product existProduct = productRepository.findById(id).orElseThrow(
                () -> new DataNotExistException("there is no product with this id: "+id)
        );

        existProduct.setName(product.getName());
        existProduct.setStock(product.getStock());
        product.setUnitPrice(product.getUnitPrice());

        Product updatedProduct = productRepository.save(existProduct);
        return mapper.toResponseDTO(updatedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findProductById(String id) {
        if(id == null || id.trim().isEmpty()){
            throw new InvalidParameterException("id can not be null or empty");
        }
        Product existProduct = productRepository.findById(id).orElseThrow(
                ()-> new DataNotExistException("there is no product with this id : "+id)
        );

        return mapper.toResponseDTO(existProduct);
    }

    @Override
    public void deleteProductById(String id) {
        if(id == null || id.trim().isEmpty()){
            throw new InvalidParameterException("id can not be null or empty");
        }
        boolean exist = productRepository.existsById(id);

        if(!exist){
           throw new DataNotExistException("there is no product with this id : "+id);
        }

        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAllProducts(DomainPageRequest pageRequest, ProductCriteria filters) {

        Sort sortDir = pageRequest.getSortDir().equalsIgnoreCase("desc")
                ? Sort.by(pageRequest.getSortBy()).descending()
                : Sort.by(pageRequest.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(pageRequest.getPage(),pageRequest.getSize(),sortDir);

        Specification<Product> specification = ProductSpecification.byFilters(filters);

        Page<Product> page = productRepository.findAll(specification,pageable);

        return page.map(productMapper::toResponseDTO);
    }
}
