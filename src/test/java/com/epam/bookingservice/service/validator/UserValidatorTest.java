package com.epam.bookingservice.service.validator;

import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.service.exception.InvalidUserException;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserValidatorTest {

    @Test
    public void validateShouldThrowValidationExceptionWhenUserEmailLengthIsNotValid() {
        String repeatedSymbol = String.join("", Collections.nCopies(300, "a"));
        User user = User.builder()
                .setName("name")
                .setEmail("email@" + repeatedSymbol + ".com")
                .setPassword("12345")
                .build();

        assertValidateThrowsInvalidUserExceptionWithReason(user, InvalidUserException.Reason.INVALID_EMAIL,
                "Email is too long but exception was not thrown");
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenUserPasswordLengthIsNotValid() {
        User user = User.builder()
                .setName("name")
                .setEmail("email@site.com")
                .setPassword("1234")
                .build();

        assertValidateThrowsInvalidUserExceptionWithReason(user, InvalidUserException.Reason.PASSWORD_TOO_SHORT,
                "Password is too short but exception was not thrown");
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenEmailIsNotValid() {
        User user = User.builder()
                .setName("name")
                .setEmail("emailsite.com")
                .setPassword("12345")
                .build();

        assertValidateThrowsInvalidUserExceptionWithReason(user, InvalidUserException.Reason.INVALID_EMAIL,
                "Email is invalid but exception was not thrown");
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenEmailIsEmpty() {
        User user = User.builder()
                .setName("name")
                .setEmail("")
                .setPassword("12345")
                .build();

        assertValidateThrowsInvalidUserExceptionWithReason(user, InvalidUserException.Reason.INVALID_EMAIL,
                "Email is empty but exception was not thrown");
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenEmailIsNull() {
        User user = User.builder()
                .setName("name")
                .setEmail(null)
                .setPassword("12345")
                .build();

        assertValidateThrowsInvalidUserExceptionWithReason(user, InvalidUserException.Reason.INVALID_EMAIL,
                "Email is null but exception was not thrown");
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenPasswordIsNull() {
        User user = User.builder()
                .setName("name")
                .setEmail("email@a.com")
                .setPassword(null)
                .build();

        assertValidateThrowsInvalidUserExceptionWithReason(user, InvalidUserException.Reason.EMPTY_PASSWORD,
                "Password is null but exception was not thrown");
    }

    private void assertValidateThrowsInvalidUserExceptionWithReason(User user, InvalidUserException.Reason reason, String failMessage) {
        UserValidator userValidator = new UserValidator();

        try {
            userValidator.validate(user);
            fail(failMessage);
        } catch (InvalidUserException e) {
            assertEquals(reason, e.getReason());
        }
    }
}