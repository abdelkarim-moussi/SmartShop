package com.app.smartshop.domain.repository;

import java.util.Optional;

public interface GenericRepository<T,ID,Filters> {
    T save(T t);
    T update(T t);
    void deleteById(ID id);
    Optional<T> findById(ID id);
    Page<T> findAll(DomainPageRequest domainPageRequest, Filters filters);
}
