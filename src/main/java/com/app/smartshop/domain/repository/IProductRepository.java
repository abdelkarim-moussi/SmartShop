package com.app.smartshop.domain.repository;
import com.app.smartshop.domain.model.search.ProductCriteria;
import com.app.smartshop.domain.model.Product;

public interface IProductRepository extends GenericRepository<Product,String, ProductCriteria>{
    Product findProductByName(String name);
    boolean existsByName(String name);
    boolean existsById(String id);
}
