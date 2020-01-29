package com.epam.app.service.validator;

import com.epam.app.entity.User;
import com.epam.app.service.exception.InvalidUserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.app.utility.StringUtility.longerThan;
import static com.epam.app.utility.StringUtility.nullOrEmpty;
import static com.epam.app.utility.StringUtility.shorterThan;

public class UserValidator implements Validator<User> {

    private static final int EMAIL_MAX_LENGTH = 254;
    private static final int PASSWORD_MIN_LENGTH = 5;

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    private static void validateEmail(String email) {
        if (nullOrEmpty(email)) {
            throwWithReason(InvalidUserException.Reason.EMPTY_EMAIL);
        }

        if (longerThan(email, EMAIL_MAX_LENGTH)) {
            throwWithReason(InvalidUserException.Reason.EMAIL_TOO_LONG);
        }

        throwWithReasonIfMatches(email, EMAIL_REGEX, InvalidUserException.Reason.INVALID_EMAIL);
    }

    private static void validatePassword(String password) {
        if (nullOrEmpty(password)) {
            throwWithReason(InvalidUserException.Reason.EMPTY_PASSWORD);
        }

        if (shorterThan(password, PASSWORD_MIN_LENGTH)) {
            throwWithReason(InvalidUserException.Reason.PASSWORD_TOO_SHORT);
        }
    }

    private static void throwWithReasonIfMatches(String string, Pattern pattern, InvalidUserException.Reason reason) {
        Matcher matcher = pattern.matcher(string);
        if (!matcher.matches()) {
            throwWithReason(reason);
        }
    }

    private static void throwWithReason(InvalidUserException.Reason reason) {
        throw new InvalidUserException(reason);
    }
}
