package com.bookingservice.entity;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FeedbackStatusEntityTest {

    @Test
    public void findByNameShouldReturnCorrectEnumValue() {
        assertThat(FeedbackStatusEntity.findByName("CREATED"), equalTo(Optional.of(FeedbackStatusEntity.CREATED)));
        assertThat(FeedbackStatusEntity.findByName("APPROVED"), equalTo(Optional.of(FeedbackStatusEntity.APPROVED)));
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfEnumValueNotFound() {
        assertThat(FeedbackStatusEntity.findByName("DELETED"), equalTo(Optional.empty()));
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfNameIsNull() {
        assertThat(FeedbackStatusEntity.findByName(null), equalTo(Optional.empty()));
    }
}