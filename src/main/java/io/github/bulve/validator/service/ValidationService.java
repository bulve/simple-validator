package io.github.bulve.validator.service;

/**
 * @author Aleksandras Vasiliauskas
 */
public interface ValidationService<T> {

    ValidationResult validate(T t);

}
