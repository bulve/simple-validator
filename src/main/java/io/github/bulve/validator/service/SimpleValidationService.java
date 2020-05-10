package io.github.bulve.validator.service;

import io.github.bulve.validator.rule.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Aleksandras Vasiliauskas
 */
public class SimpleValidationService<T> implements ValidationService<T> {

    private Collection<ValidationRule<T>> validationRules;
    private ValidationSeverity validationSeverity;

    public SimpleValidationService(RuleService<T> ruleService, ValidationSeverity validationSeverity) {
        this.validationRules = ruleService.getValidationRules();
        this.validationSeverity = validationSeverity;
    }

    public SimpleValidationService(RuleService<T> ruleService) {
        this.validationRules = ruleService.getValidationRules();
        this.validationSeverity = ValidationSeverity.ERROR;
    }

    @Override
    public ValidationResult validate(T toValidate) {
        Set<ValidationError> validationErrors = validationRules.stream()
                .map(rule -> validate(rule, toValidate))
                .filter(result -> !result.isValid())
                .filter(result -> isMatchingSeverity(result.getSeverity()))
                .map(result -> new ValidationError(result.getMessage(), result.getSeverity()))
                .collect(Collectors.toSet());

        return new ValidationResultImpl(validationErrors);
    }

    private RuleValidationResult validate(ValidationRule<T> rule, T toValidate) {
        RuleValidationResult ruleValidationResult;
        try {
            ruleValidationResult = rule.validate(toValidate);
        } catch (Exception ex) {
            ruleValidationResult = RuleValidationResultImpl.failureResult("Validation failure with " + ex.getMessage());
        }
        return ruleValidationResult;
    }

    private boolean isMatchingSeverity(ValidationSeverity validationSeverity){
        switch (this.validationSeverity) {
            case ERROR:
                return validationSeverity.equals(ValidationSeverity.ERROR);
            case WARNING:
                return validationSeverity.equals(ValidationSeverity.ERROR)
                        || validationSeverity.equals(ValidationSeverity.WARNING);
            case INFO:
            default:
                return true;
        }
    }
}
