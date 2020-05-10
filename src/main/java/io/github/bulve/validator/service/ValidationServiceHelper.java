package io.github.bulve.validator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aleksandras Vasiliauskas
 */
public class ValidationServiceHelper {

    private List<ValidationElement> validationElements;

    private ValidationServiceHelper() {
        this.validationElements = new ArrayList<>();
    }

    public static ValidationServiceHelper validator(){
        return new ValidationServiceHelper();
    }

    public <T> ValidationServiceHelper with(T instance, ValidationService<T> validationService) {
        validationElements.add(new ValidationElement<>(instance, validationService));
        return this;
    }

    public ValidationResult validate() {
        List<ValidationError> validationErrors = validationElements.stream()
                .map(ValidationElement::validate)
                .filter(result -> !result.isValid())
                .flatMap(result -> result.getValidationErrors().stream())
                .collect(Collectors.toList());

        return new ValidationResultImpl(validationErrors);
    }

    private static class ValidationElement<T> {
        private T instance;
        private ValidationService<T> validationService;

        public ValidationElement(T instance, ValidationService<T> validationService) {
            this.instance = instance;
            this.validationService = validationService;
        }

        public ValidationResult validate() {
            return validationService.validate(instance);
        }
    }

}
