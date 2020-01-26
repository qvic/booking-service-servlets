package com.epam.app.service.validator;

public interface Validator<E> {

    void validate(E entity);
}
