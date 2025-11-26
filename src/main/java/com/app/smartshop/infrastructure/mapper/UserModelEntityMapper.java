package com.app.smartshop.infrastructure.mapper;

import com.app.smartshop.domain.model.User;
import com.app.smartshop.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserModelEntityMapper {
    User toModel(UserEntity entity);
    UserEntity toEntity(User user);
}
