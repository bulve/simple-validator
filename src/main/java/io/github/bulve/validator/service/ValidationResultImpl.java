package io.github.bulve.validator.service;

import java.util.Collection;

/**
 * @author Aleksandras Vasiliauskas
 */
public class ValidationResultImpl implements ValidationResult {

    private boolean valid;
    private Collection<ValidationError> validationErrors;

    public ValidationResultImpl(Collection<ValidationError> validationErrors) {
        this.valid = validationErrors != null && validationErrors.size() == 0;
        this.validationErrors = validationErrors;
    }

//    public ValidationResultImpl() {
//        this.valid = true;
//        this.validationErrors = Collections.emptyList();
//    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public Collection<ValidationError> getValidationErrors() {
        return validationErrors;
    }
}
