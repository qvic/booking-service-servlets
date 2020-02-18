package com.bookingservice.mapper;

import com.bookingservice.domain.Service;
import com.bookingservice.entity.ServiceEntity;

import java.time.Duration;

public class ServiceMapper implements Mapper<ServiceEntity, Service> {

    @Override
    public ServiceEntity mapDomainToEntity(Service service) {
        if (service == null) {
            return null;
        }

        return ServiceEntity.builder()
                .setId(service.getId())
                .setName(service.getName())
                .setDurationMinutes(mapDurationMinutes(service.getDuration()))
                .setPrice(service.getPrice())
                .build();
    }

    private Integer mapDurationMinutes(Duration duration) {
        return (duration == null) ? null :
                Math.toIntExact(duration.toMinutes());
    }

    @Override
    public Service mapEntityToDomain(ServiceEntity service) {
        if (service == null) {
            return null;
        }

        return Service.builder()
                .setId(service.getId())
                .setName(service.getName())
                .setDuration(mapDuration(service.getDurationMinutes()))
                .setPrice(service.getPrice())
                .build();
    }

    private Duration mapDuration(Integer durationMinutes) {
        return (durationMinutes == null) ? null :
                Duration.ofMinutes(durationMinutes);
    }
}
