package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.model.User;

import java.util.Optional;

public interface IUserRepository extends GenericRepository<User,String>{
    public Optional<User> findByUserName(String userName);
}
