package com.app.smartshop.infrastructure.persistence.repository;
import com.app.smartshop.domain.model.User;
import com.app.smartshop.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserNameAndHashedPassword(String username,String password);
}
