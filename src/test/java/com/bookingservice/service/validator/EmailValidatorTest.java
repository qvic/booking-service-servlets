package com.bookingservice.service.validator;

import com.bookingservice.service.exception.EmailValidationException;
import com.bookingservice.service.exception.ValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static org.junit.Assert.*;

public class EmailValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validateShouldThrowCustomValidationExceptionWhenEmailIsNotValid() {
        EmailValidator emailValidator = new EmailValidator();

        String email = "testemail.com";

        expectedException.expect(EmailValidationException.class);
        emailValidator.validate(email);
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenEmailIsNotValid() {
        EmailValidator emailValidator = new EmailValidator();

        String email = "testemail.com";

        expectedException.expect(EmailValidationException.class);
        emailValidator.validate(email);
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenEmailIsEmpty() {
        EmailValidator emailValidator = new EmailValidator();

        String email = "";

        expectedException.expect(EmailValidationException.class);
        emailValidator.validate(email);
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenEmailIsNull() {
        EmailValidator emailValidator = new EmailValidator();

        String email = null;

        expectedException.expect(EmailValidationException.class);
        emailValidator.validate(email);
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenEmailIsTooLong() {
        EmailValidator emailValidator = new EmailValidator();

        String email = String.join("", Collections.nCopies(300, "a")) + "@email.com";

        expectedException.expect(EmailValidationException.class);
        emailValidator.validate(email);
    }

    @Test
    public void validateShouldDoNothingWhenEmailIsCorrect() {
        EmailValidator emailValidator = new EmailValidator();

        String email = "correct_email@site.com";

        emailValidator.validate(email);
    }
}