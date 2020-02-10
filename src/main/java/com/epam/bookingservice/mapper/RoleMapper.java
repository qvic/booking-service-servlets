package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Role;
import com.epam.bookingservice.entity.RoleEntity;

public class RoleMapper implements Mapper<RoleEntity, Role> {

    @Override
    public RoleEntity mapDomainToEntity(Role domain) {
        if (domain == null) {
            return null;
        }

        return RoleEntity.valueOf(domain.name());
    }

    @Override
    public Role mapEntityToDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return Role.valueOf(entity.name());
    }
}
