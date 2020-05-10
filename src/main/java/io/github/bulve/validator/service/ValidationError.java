package io.github.bulve.validator.service;

import io.github.bulve.validator.rule.ValidationSeverity;

import java.util.Objects;

/**
 * @author Aleksandras Vasiliauskas
 */
public class ValidationError {
    private String validationMessage;
    private ValidationSeverity validationSeverity;

    public ValidationError(String validationMessage, ValidationSeverity validationSeverity) {
        this.validationMessage = validationMessage;
        this.validationSeverity = validationSeverity;
    }

    public ValidationSeverity getValidationSeverity() {
        return validationSeverity;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError error = (ValidationError) o;
        return Objects.equals(validationMessage, error.validationMessage) &&
                validationSeverity == error.validationSeverity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationMessage, validationSeverity);
    }
}
