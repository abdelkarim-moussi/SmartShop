package com.app.smartshop.domain.repository;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<T,ID> {
    public T save(T t);
    public void delete(T t);
    public Optional<T> findById(ID id);
    public List<T> findAll();
}
