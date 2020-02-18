package com.bookingservice.service.validator;

import com.bookingservice.domain.UserLoginForm;

public class UserLoginFormValidator implements Validator<UserLoginForm> {

    private final Validator<String> emailValidator;
    private final Validator<String> passwordValidator;

    public UserLoginFormValidator(Validator<String> emailValidator, Validator<String> passwordValidator) {
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public void validate(UserLoginForm loginForm) {
        validatePassword(loginForm.getPassword());
        validateEmail(loginForm.getEmail());
    }

    private void validateEmail(String email) {
        emailValidator.validate(email);
    }

    private void validatePassword(String password) {
        passwordValidator.validate(password);
    }
}
