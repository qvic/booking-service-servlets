package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.RoleEntity;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.entity.UserStatusEntity;

public class UserMapper implements Mapper<UserEntity, User> {

    @Override
    public UserEntity mapDomainToEntity(User domain) {
        return UserEntity.builder()
                .setId(domain.getId())
                .setName(domain.getName())
                .setEmail(domain.getEmail())
                .setPassword(domain.getPassword())
                .setRole(RoleEntity.CLIENT)
                .setStatus(UserStatusEntity.ACTIVE)
                .build();
    }

    @Override
    public User mapEntityToDomain(UserEntity entity) {
        return User.builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setPassword(entity.getPassword())
                .build();
    }
}
