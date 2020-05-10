package io.github.bulve.validator.service;

import java.util.Collection;

/**
 * @author Aleksandras Vasiliauskas
 */
public interface ValidationResult {

    boolean isValid();

    Collection<ValidationError> getValidationErrors();
}
