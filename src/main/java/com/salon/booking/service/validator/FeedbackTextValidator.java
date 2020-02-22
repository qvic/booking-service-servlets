package com.salon.booking.service.validator;

import com.salon.booking.service.exception.ValidationException;

import static com.salon.booking.utility.StringUtility.nullOrEmpty;

public class FeedbackTextValidator implements Validator<String> {

    @Override
    public void validate(String s) {
        if (nullOrEmpty(s)) {
            throw new ValidationException("Feedback text should be non-empty!", "validation.feedback_non_empty");
        }
    }
}
