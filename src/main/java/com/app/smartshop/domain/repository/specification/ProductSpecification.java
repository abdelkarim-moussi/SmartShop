package com.app.smartshop.domain.repository.specification;

import com.app.smartshop.domain.entity.Product;
import com.app.smartshop.domain.entity.search.ProductCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> byFilters(ProductCriteria filters){

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(filters.getName() != null && !filters.getName().isEmpty()){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),"%"+filters.getName().toLowerCase()+"%"
                ));
            }

            if(filters.getUnitPrice() != null && filters.getUnitPrice().compareTo(BigDecimal.ZERO) > 0){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("unitPrice"),
                        filters.getUnitPrice()
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
