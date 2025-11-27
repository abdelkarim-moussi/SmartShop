package com.app.smartshop.domain.repository;
import com.app.smartshop.application.dto.client.Filters;
import com.app.smartshop.domain.repository.specification.Page;
import com.app.smartshop.domain.repository.specification.DomainPageRequest;

import java.util.Optional;

public interface GenericRepository<T,ID> {
    public T save(T t);
    public T update(T t);
    public void deleteById(ID id);
    public Optional<T> findById(ID id);
    public Page<T> findAll(DomainPageRequest domainPageRequest, Filters filters);
}
