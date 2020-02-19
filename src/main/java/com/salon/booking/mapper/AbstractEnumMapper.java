package com.salon.booking.mapper;

import com.salon.booking.utility.EnumUtility;

abstract class AbstractEnumMapper<E extends Enum<E>, D extends Enum<D>> implements Mapper<E, D> {

    private final E[] entityValues;
    private final D[] domainValues;

    AbstractEnumMapper(E[] entityValues, D[] domainValues) {
        this.domainValues = domainValues;
        this.entityValues = entityValues;
    }

    @Override
    public E mapDomainToEntity(D domain) {
        if (domain == null) {
            return null;
        }

        return EnumUtility.findByName(domain.name(), entityValues).orElse(null);
    }

    @Override
    public D mapEntityToDomain(E entity) {
        if (entity == null) {
            return null;
        }

        return EnumUtility.findByName(entity.name(), domainValues).orElse(null);
    }
}
