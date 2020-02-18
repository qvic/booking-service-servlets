package com.bookingservice.service.validator;

import com.bookingservice.service.exception.EmailValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bookingservice.utility.StringUtility.longerThan;
import static com.bookingservice.utility.StringUtility.nullOrEmpty;

public class EmailValidator implements Validator<String> {

    private static final String EXCEPTION_MESSAGE = "Invalid email";
    private static final String EXCEPTION_L10N_KEY = "validation.invalid_email";

    private static final int EMAIL_MAX_LENGTH = 254;
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(String s) {
        throwIf(nullOrEmpty(s) || longerThan(EMAIL_MAX_LENGTH, s));
        throwIfMatches(s);
    }

    private void throwIfMatches(String string) {
        Matcher matcher = EmailValidator.EMAIL_REGEX.matcher(string);
        throwIf(!matcher.matches());
    }

    private static void throwIf(boolean condition) {
        if (condition) {
            throw new EmailValidationException(EXCEPTION_MESSAGE, EXCEPTION_L10N_KEY);
        }
    }
}
