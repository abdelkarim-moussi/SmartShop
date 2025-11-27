package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.model.User;

import java.util.Optional;

public interface IUserRepository extends GenericRepository<User,String>{
    void deleteById(String id);

    public Optional<User> findByUserName(String userName);
    public Optional<User> findByUserNameAndPassword(String userName, String password);
}
