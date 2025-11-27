package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.client.*;
import com.app.smartshop.domain.repository.specification.DomainPageRequest;
import com.app.smartshop.domain.repository.specification.Page;

public interface IProductService {
    ProductResponseDTO createProduct(ProductRequestDTO product);
    ProductResponseDTO updateProduct(String id,ProductRequestDTO product);
    ProductResponseDTO findProductById(String id);
    void deleteProductById(String id);
    Page<ProductResponseDTO> findAllProducts(DomainPageRequest domainPageRequest, ProductFilters filters);
}
