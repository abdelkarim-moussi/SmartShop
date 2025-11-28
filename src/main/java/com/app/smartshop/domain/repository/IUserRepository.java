package com.app.smartshop.domain.repository;

import com.app.smartshop.domain.model.search.UserCriteria;
import com.app.smartshop.domain.model.User;

import java.util.Optional;

public interface IUserRepository extends GenericRepository<User,String, UserCriteria>{
    void deleteById(String id);

    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNameAndPassword(String userName, String password);
}
