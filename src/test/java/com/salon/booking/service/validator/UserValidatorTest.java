package com.salon.booking.service.validator;

import com.salon.booking.domain.User;
import com.salon.booking.service.exception.UserValidationException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private PasswordValidator passwordValidator;

    @After
    public void resetMocks() {
        Mockito.reset(emailValidator, passwordValidator);
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenUserNameIsTooShort() {
        User user = User.builder()
                .setName("Aa")
                .build();

        UserValidator userValidator = new UserValidator(emailValidator, passwordValidator);

        expectedException.expect(UserValidationException.class);
        userValidator.validate(user);
    }

    @Test
    public void validateShouldThrowValidationExceptionWhenUserNameIsTooLong() {
        String name = String.join("", Collections.nCopies(300, "a"));
        User user = User.builder()
                .setName(name)
                .build();

        UserValidator userValidator = new UserValidator(emailValidator, passwordValidator);

        expectedException.expect(UserValidationException.class);
        userValidator.validate(user);
    }
}