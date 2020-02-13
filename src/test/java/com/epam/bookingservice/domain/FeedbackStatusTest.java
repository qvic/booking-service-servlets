package com.epam.bookingservice.domain;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FeedbackStatusTest {

    @Test
    public void findByNameShouldReturnCorrectEnumValue() {
        assertThat(FeedbackStatus.findByName("CREATED"), equalTo(Optional.of(FeedbackStatus.CREATED)));
        assertThat(FeedbackStatus.findByName("APPROVED"), equalTo(Optional.of(FeedbackStatus.APPROVED)));
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfEnumValueNotFound() {
        assertThat(FeedbackStatus.findByName("DELETED"), equalTo(Optional.empty()));
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfNameIsNull() {
        assertThat(FeedbackStatus.findByName(null), equalTo(Optional.empty()));
    }
}