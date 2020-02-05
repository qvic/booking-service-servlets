package com.epam.bookingservice.service.validator;

import com.epam.bookingservice.service.exception.EmailValidationException;
import com.epam.bookingservice.service.exception.ValidationException;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.bookingservice.utility.StringUtility.longerThan;
import static com.epam.bookingservice.utility.StringUtility.nullOrEmpty;

public class EmailValidator implements Validator<String> {

    private static final int EMAIL_MAX_LENGTH = 254;

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    private Supplier<ValidationException> exceptionSupplier;

    public EmailValidator(Supplier<ValidationException> exceptionSupplier) {
        this.exceptionSupplier = exceptionSupplier;
    }

    public EmailValidator() {
        this.exceptionSupplier = () -> new EmailValidationException("Invalid email");
    }

    @Override
    public void validate(String s) {
        if (nullOrEmpty(s)) {
            throw exceptionSupplier.get();
        }

        if (longerThan(s, EMAIL_MAX_LENGTH)) {
            throw exceptionSupplier.get();
        }

        throwIfMatches(s);
    }

    private void throwIfMatches(String string) {
        Matcher matcher = EmailValidator.EMAIL_REGEX.matcher(string);
        if (!matcher.matches()) {
            throw exceptionSupplier.get();
        }
    }
}
