package ru.practicum.shareit.booking.validator;

import javax.validation.Valid;

public interface Validator<T> {
    void check(@Valid T t);
}
