package com.app.smartshop.infrastructure.persistence.repository.specification;

import com.app.smartshop.domain.model.search.ProductCriteria;
import com.app.smartshop.infrastructure.persistence.entity.ProductEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<ProductEntity> byFilters(ProductCriteria filters){

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
