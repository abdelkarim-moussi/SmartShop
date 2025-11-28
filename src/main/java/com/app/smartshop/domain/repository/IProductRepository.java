package com.app.smartshop.domain.repository;
import com.app.smartshop.application.dto.client.ProductFilters;
import com.app.smartshop.domain.model.Product;

public interface IProductRepository extends GenericRepository<Product,String, ProductFilters>{
    Product findProductByName(String name);
    boolean existsByName(String name);
    boolean existsById(String id);
}
