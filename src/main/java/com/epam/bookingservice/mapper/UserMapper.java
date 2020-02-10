package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Role;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.RoleEntity;
import com.epam.bookingservice.entity.UserEntity;

public class UserMapper implements Mapper<UserEntity, User> {

    private final Mapper<RoleEntity, Role> roleMapper;

    public UserMapper(Mapper<RoleEntity, Role> roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public UserEntity mapDomainToEntity(User domain) {
        if (domain == null) {
            return null;
        }

        return UserEntity.builder()
                .setId(domain.getId())
                .setName(domain.getName())
                .setEmail(domain.getEmail())
                .setPassword(domain.getPassword())
                .setRole(roleMapper.mapDomainToEntity(domain.getRole()))
                .build();
    }

    @Override
    public User mapEntityToDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setPassword(entity.getPassword())
                .setRole(roleMapper.mapEntityToDomain(entity.getRole()))
                .build();
    }
}
