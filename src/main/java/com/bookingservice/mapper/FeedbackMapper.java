package com.bookingservice.mapper;

import com.bookingservice.domain.Feedback;
import com.bookingservice.domain.User;
import com.bookingservice.entity.FeedbackEntity;
import com.bookingservice.entity.FeedbackStatusEntity;
import com.bookingservice.entity.UserEntity;

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
