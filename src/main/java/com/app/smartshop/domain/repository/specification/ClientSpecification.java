package com.app.smartshop.domain.repository.specification;

import com.app.smartshop.domain.entity.Client;
import com.app.smartshop.domain.entity.search.ClientCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class ClientSpecification {
    public static Specification<Client> byFilters(ClientCriteria filters){

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(filters.getLoyaltyLevel() != null && !filters.getLoyaltyLevel().isEmpty()){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),"%"+filters.getLoyaltyLevel().toLowerCase()+"%"
                ));
            }

            if(filters.getSearch() != null){
                predicates.add(criteriaBuilder.like(
                        root.get("search"),
                        filters.getSearch()
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
