package com.bookingservice.mapper;

public interface Mapper<E, D> {

    E mapDomainToEntity(D domain);

    D mapEntityToDomain(E entity);
}
