package com.app.smartshop.infrastructure.persistence.repository;

import com.app.smartshop.application.dto.client.ClientFilters;
import com.app.smartshop.domain.model.User;
import com.app.smartshop.domain.repository.IUserRepository;
import com.app.smartshop.domain.repository.specification.DomainPageRequest;
import com.app.smartshop.domain.repository.specification.Page;
import com.app.smartshop.infrastructure.mapper.UserModelEntityMapper;
import com.app.smartshop.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {
    private final JpaUserRepository userRepository;
    private final UserModelEntityMapper userModelEntityMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userModelEntityMapper.toEntity(user);
        return userModelEntityMapper.toModel(userRepository.save(entity));
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id).map(userModelEntityMapper::toModel);
    }

    @Override
    public Page<User> findAll(DomainPageRequest pageRequest, ClientFilters clientFilters) {
        return null;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName).map(userModelEntityMapper::toModel);
    }

    @Override
    public Optional<User> findByUserNameAndPassword(String userName, String password){
        return userRepository.findByUserNameAndHashedPassword(userName,password).map(userModelEntityMapper::toModel);
    }
}
