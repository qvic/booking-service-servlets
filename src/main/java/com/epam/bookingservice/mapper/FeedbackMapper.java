package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Feedback;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.FeedbackEntity;
import com.epam.bookingservice.entity.FeedbackStatusEntity;
import com.epam.bookingservice.entity.UserEntity;

public class FeedbackMapper implements Mapper<FeedbackEntity, Feedback> {

    private final Mapper<UserEntity, User> userMapper;

    public FeedbackMapper(Mapper<UserEntity, User> userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public FeedbackEntity mapDomainToEntity(Feedback feedback) {
        if (feedback == null) {
            return null;
        }

        return FeedbackEntity.builder()
                .setText(feedback.getText())
                .setStatus(FeedbackStatusEntity.CREATED)
                .setWorker(userMapper.mapDomainToEntity(feedback.getWorker()))
                .build();
    }

    @Override
    public Feedback mapEntityToDomain(FeedbackEntity feedback) {
        if (feedback == null) {
            return null;
        }

        return new Feedback(
                feedback.getText(),
                userMapper.mapEntityToDomain(feedback.getWorker())
        );
    }
}
