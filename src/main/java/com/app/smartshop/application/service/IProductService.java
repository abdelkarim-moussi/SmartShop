package com.app.smartshop.application.service;

import com.app.smartshop.infrastructure.controller.dto.DomainPageRequest;
import com.app.smartshop.infrastructure.controller.dto.Page;
import com.app.smartshop.domain.model.search.ProductCriteria;
import com.app.smartshop.infrastructure.controller.dto.ProductRequestDTO;
import com.app.smartshop.infrastructure.controller.dto.ProductResponseDTO;

public interface IProductService {
    ProductResponseDTO createProduct(ProductRequestDTO product);
    ProductResponseDTO updateProduct(String id,ProductRequestDTO product);
    ProductResponseDTO findProductById(String id);
    void deleteProductById(String id);
    Page<ProductResponseDTO> findAllProducts(DomainPageRequest domainPageRequest, ProductCriteria filters);
}
