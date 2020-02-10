package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Feedback;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.FeedbackEntity;
import com.epam.bookingservice.entity.FeedbackStatusEntity;
import com.epam.bookingservice.entity.UserEntity;

public class FeedbackMapper implements Mapper<FeedbackEntity, Feedback> {

    private Mapper<UserEntity, User> userMapper;

    @Override
    public FeedbackEntity mapDomainToEntity(Feedback domain) {
        if (domain == null) {
            return null;
        }

        return FeedbackEntity.builder()
                .setText(domain.getText())
                .setStatus(FeedbackStatusEntity.CREATED)
                .setWorker(userMapper.mapDomainToEntity(domain.getWorker()))
                .build();
    }

    @Override
    public Feedback mapEntityToDomain(FeedbackEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Feedback(
                entity.getText(),
                userMapper.mapEntityToDomain(entity.getWorker())
        );
    }
}
