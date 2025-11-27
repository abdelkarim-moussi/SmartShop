package com.app.smartshop.domain.repository;
import com.app.smartshop.application.dto.client.ClientFilters;
import com.app.smartshop.domain.repository.specification.Page;
import com.app.smartshop.domain.repository.specification.DomainPageRequest;

import java.util.Optional;

public interface GenericRepository<T,ID> {
    T save(T t);
    T update(T t);
    void deleteById(ID id);
    Optional<T> findById(ID id);
    Page<T> findAll(DomainPageRequest domainPageRequest, ClientFilters clientFilters);
}
