package com.salon.booking.service.validator;

import com.salon.booking.service.exception.PasswordValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

public class PasswordValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validateShouldThrowValidationExceptionWhenPasswordIsNull() {
        PasswordValidator validator = new PasswordValidator();

        expectedException.expect(PasswordValidationException.class);
        validator.validate(null);
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenPasswordIsTooShort() {
        PasswordValidator validator = new PasswordValidator();

        expectedException.expect(PasswordValidationException.class);
        validator.validate("abc");
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenPasswordIsTooLong() {
        String password = String.join("", Collections.nCopies(300, "a"));

        PasswordValidator validator = new PasswordValidator();

        expectedException.expect(PasswordValidationException.class);
        validator.validate(password);
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenPasswordHasNoDigits() {
        PasswordValidator validator = new PasswordValidator();

        expectedException.expect(PasswordValidationException.class);
        validator.validate("abcdsdfsfdsffsdf");
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenPasswordHasNoCharacters() {
        PasswordValidator validator = new PasswordValidator();

        expectedException.expect(PasswordValidationException.class);
        validator.validate("1322133123131323");
    }

    @Test
    public void validateShouldDoNothingWhenPasswordIsValid() {
        PasswordValidator validator = new PasswordValidator();

        validator.validate("acbbc234234bbb");
    }

    @Test
    public void validateShouldAcceptCyrillicPassword() {
        PasswordValidator validator = new PasswordValidator();

        validator.validate("пароль123");
    }
}