package com.bookingservice.mapper;

import com.bookingservice.domain.Role;
import com.bookingservice.entity.RoleEntity;

public class RoleMapper implements Mapper<RoleEntity, Role> {

    @Override
    public RoleEntity mapDomainToEntity(Role role) {
        if (role == null) {
            return null;
        }

        return RoleEntity.findByName(role.name()).orElse(null);
    }

    @Override
    public Role mapEntityToDomain(RoleEntity role) {
        if (role == null) {
            return null;
        }

        return Role.findByName(role.name()).orElse(null);
    }
}
