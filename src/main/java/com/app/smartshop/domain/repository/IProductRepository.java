package com.app.smartshop.domain.repository;
import com.app.smartshop.domain.model.Product;

public interface IProductRepository extends GenericRepository<Product,String>{
    Product findProductByName(String name);
    boolean existsByName(String name);
}
