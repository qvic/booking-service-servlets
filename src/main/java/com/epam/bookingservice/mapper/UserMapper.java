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
    public UserEntity mapDomainToEntity(User user) {
        if (user == null) {
            return null;
        }

        return UserEntity.builder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setRole(roleMapper.mapDomainToEntity(user.getRole()))
                .build();
    }

    @Override
    public User mapEntityToDomain(UserEntity user) {
        if (user == null) {
            return null;
        }

        return User.builder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setRole(roleMapper.mapEntityToDomain(user.getRole()))
                .build();
    }
}
