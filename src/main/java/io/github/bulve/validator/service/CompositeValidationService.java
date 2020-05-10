package io.github.bulve.validator.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Composite validator used to compose many validators for the same {@link T} type.
 *
 * @author Aleksandras Vasiliauskas
 */
public class CompositeValidationService<T> implements ValidationService<T>{

    private Collection<ValidationService<T>> validationServices;

    public CompositeValidationService(Collection<ValidationService<T>> validationServices) {
        this.validationServices = validationServices;
    }

    @Override
    public ValidationResult validate(T t) {
        List<ValidationError> validationErrors = validationServices.stream()
                .map(service -> service.validate(t))
                .filter(result -> !result.isValid())
                .flatMap(result -> result.getValidationErrors().stream())
                .collect(Collectors.toList());
        return new ValidationResultImpl(validationErrors);
    }
}
