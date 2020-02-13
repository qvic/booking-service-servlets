package com.epam.bookingservice.service.validator;

import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.service.exception.InvalidUserException;

import static com.epam.bookingservice.utility.StringUtility.longerThan;
import static com.epam.bookingservice.utility.StringUtility.nullOrEmpty;
import static com.epam.bookingservice.utility.StringUtility.shorterThan;
import static java.util.Objects.isNull;

public class UserValidator implements Validator<User> {

    private static final int NAME_MAX_LENGTH = 200;
    private static final int PASSWORD_MIN_LENGTH = 5;

    private EmailValidator emailValidator;

    public UserValidator() {
        this.emailValidator = new EmailValidator(() -> new InvalidUserException(InvalidUserException.Reason.INVALID_EMAIL));
    }

    @Override
    public void validate(User user) {
        validateName(user.getName());
        validatePassword(user.getPassword());
        emailValidator.validate(user.getEmail());
    }

    private static void validatePassword(String password) {
        throwWithReasonIf(isNull(password), InvalidUserException.Reason.EMPTY_PASSWORD);
        throwWithReasonIf(nullOrEmpty(password), InvalidUserException.Reason.EMPTY_PASSWORD);
        throwWithReasonIf(shorterThan(password, PASSWORD_MIN_LENGTH), InvalidUserException.Reason.PASSWORD_TOO_SHORT);
    }

    private static void validateName(String name) {
        throwWithReasonIf(isNull(name), InvalidUserException.Reason.EMPTY_NAME);
        throwWithReasonIf(longerThan(name, NAME_MAX_LENGTH), InvalidUserException.Reason.NAME_TOO_LONG);
    }

    private static void throwWithReasonIf(boolean condition, InvalidUserException.Reason reason) {
        if (condition) {
            throwWithReason(reason);
        }
    }

    private static void throwWithReason(InvalidUserException.Reason reason) {
        throw new InvalidUserException(reason);
    }
}
