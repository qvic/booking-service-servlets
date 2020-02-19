package com.salon.booking.service.validator;

import com.salon.booking.domain.UserLoginForm;

public class UserLoginFormValidator implements Validator<UserLoginForm> {

    private final Validator<String> emailValidator;

    public UserLoginFormValidator(Validator<String> emailValidator) {
        this.emailValidator = emailValidator;
    }

    @Override
    public void validate(UserLoginForm loginForm) {
        validateEmail(loginForm.getEmail());
    }

    private void validateEmail(String email) {
        emailValidator.validate(email);
    }
}
