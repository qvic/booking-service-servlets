package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.entity.ServiceEntity;

public class ServiceMapper implements Mapper<ServiceEntity, Service> {

    @Override
    public ServiceEntity mapDomainToEntity(Service domain) {
        if (domain == null) {
            return null;
        }

        return ServiceEntity.builder()
                .setId(domain.getId())
                .setName(domain.getName())
                .setDurationInTimeslots(domain.getDurationInTimeslots())
                .setPrice(domain.getPrice())
                .build();
    }

    @Override
    public Service mapEntityToDomain(ServiceEntity entity) {
        if (entity == null) {
            return null;
        }

        return Service.builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setDurationInTimeslots(entity.getDurationInTimeslots())
                .setPrice(entity.getPrice())
                .build();
    }
}
