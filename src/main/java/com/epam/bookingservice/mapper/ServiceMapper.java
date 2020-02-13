package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.entity.ServiceEntity;

public class ServiceMapper implements Mapper<ServiceEntity, Service> {

    @Override
    public ServiceEntity mapDomainToEntity(Service service) {
        if (service == null) {
            return null;
        }

        return ServiceEntity.builder()
                .setId(service.getId())
                .setName(service.getName())
                .setDurationInTimeslots(service.getDurationInTimeslots())
                .setPrice(service.getPrice())
                .build();
    }

    @Override
    public Service mapEntityToDomain(ServiceEntity service) {
        if (service == null) {
            return null;
        }

        return Service.builder()
                .setId(service.getId())
                .setName(service.getName())
                .setDurationInTimeslots(service.getDurationInTimeslots())
                .setPrice(service.getPrice())
                .build();
    }
}
