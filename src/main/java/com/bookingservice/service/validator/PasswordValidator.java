package com.bookingservice.service.validator;

import com.bookingservice.service.exception.PasswordValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bookingservice.utility.StringUtility.nullOrEmpty;

public class PasswordValidator implements Validator<String> {

    private static final String EXCEPTION_MESSAGE = "Invalid password. Password length should be between 5 and 200 symbols, at least one letter and one digit";
    private static final String EXCEPTION_L10N_KEY = "validation.invalid_password";

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*\\p{L})(?=.*\\d)[\\p{L}\\d]{5,200}$");

    @Override
    public void validate(String s) {
        throwIf(nullOrEmpty(s));
        throwIfMatches(s);
    }

    private void throwIfMatches(String string) {
        Matcher matcher = PASSWORD_REGEX.matcher(string);
        throwIf(!matcher.matches());
    }

    private static void throwIf(boolean condition) {
        if (condition) {
            throw new PasswordValidationException(EXCEPTION_MESSAGE, EXCEPTION_L10N_KEY);
        }
    }
}
