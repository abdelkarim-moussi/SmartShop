package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.application.dto.Page;
import com.app.smartshop.domain.entity.search.ProductCriteria;
import com.app.smartshop.application.dto.ProductRequestDTO;
import com.app.smartshop.application.dto.ProductResponseDTO;

public interface IProductService {
    ProductResponseDTO createProduct(ProductRequestDTO product);
    ProductResponseDTO updateProduct(String id,ProductRequestDTO product);
    ProductResponseDTO findProductById(String id);
    void deleteProductById(String id);
    Page<ProductResponseDTO> findAllProducts(DomainPageRequest domainPageRequest, ProductCriteria filters);
}
