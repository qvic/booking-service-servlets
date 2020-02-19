package com.salon.booking.service.validator;

import com.salon.booking.domain.User;
import com.salon.booking.service.exception.UserValidationException;
import com.salon.booking.utility.StringUtility;

import static java.util.Objects.isNull;

public class UserValidator implements Validator<User> {

    private static final int NAME_MIN_LENGTH = 4;
    private static final int NAME_MAX_LENGTH = 200;

    private final Validator<String> emailValidator;
    private final Validator<String> passwordValidator;

    public UserValidator(Validator<String> emailValidator, Validator<String> passwordValidator) {
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public void validate(User user) {
        validateName(user.getName());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
    }

    private void validateEmail(String email) {
        emailValidator.validate(email);
    }

    private void validatePassword(String password) {
        passwordValidator.validate(password);
    }

    private void validateName(String name) {
        throwIf(isNull(name), "Name is empty", "validation.empty_name");
        throwIf(StringUtility.shorterThan(NAME_MIN_LENGTH, name) || StringUtility.longerThan(NAME_MAX_LENGTH, name),
                "Name should be between 4 and 200 symbols", "validation.invalid_name");
    }

    private static void throwIf(boolean condition, String message, String localizationKey) {
        if (condition) {
            throwWithMessage(message, localizationKey);
        }
    }

    private static void throwWithMessage(String message, String localizationKey) {
        throw new UserValidationException(message, localizationKey);
    }
}
