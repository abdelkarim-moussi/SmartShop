package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.client.ProductFilters;
import com.app.smartshop.application.dto.client.ProductRequestDTO;
import com.app.smartshop.application.dto.client.ProductResponseDTO;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.exception.ProductExistByNameException;
import com.app.smartshop.application.mapper.ProductModelDTOMapper;
import com.app.smartshop.domain.model.Product;
import com.app.smartshop.domain.repository.IProductRepository;
import com.app.smartshop.domain.repository.specification.DomainPageRequest;
import com.app.smartshop.domain.repository.specification.Page;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements IProductService{
    private final IProductRepository productRepository;
    private final ProductModelDTOMapper mapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO product) {
        if(product == null){
            throw new InvalidParameterException("product data can not be null");
        }
        boolean exist = productRepository.existsByName(product.getName());

        if(exist){
            throw new ProductExistByNameException("there is already a product with this name: "+product.getName()+"\ntry to increase the stock instead ");
        }

        Product savedProduct = productRepository.save(mapper.toDomainModel(product));

        return mapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO product) {
        return null;
    }

    @Override
    public ProductResponseDTO findProductById(String id) {
        return null;
    }

    @Override
    public void deleteProductById(String id) {

    }

    @Override
    public Page<ProductResponseDTO> findAllProducts(DomainPageRequest domainPageRequest, ProductFilters filters) {
        return null;
    }
}
