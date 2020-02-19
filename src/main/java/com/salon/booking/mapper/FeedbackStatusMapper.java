package com.salon.booking.mapper;

import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.entity.FeedbackStatusEntity;

public class FeedbackStatusMapper extends AbstractEnumMapper<FeedbackStatusEntity, FeedbackStatus> {
    
    public FeedbackStatusMapper() {
        super(FeedbackStatusEntity.values(), FeedbackStatus.values());
    }
}
