package com.epam.bookingservice.service.validator;

public interface Validator<E> {

    void validate(E e);
}
