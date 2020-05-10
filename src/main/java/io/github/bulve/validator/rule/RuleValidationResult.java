package io.github.bulve.validator.rule;

/**
 * @author Aleksandras Vasiliauskas
 */
public interface RuleValidationResult {

    boolean isValid();

    String getMessage();

    default ValidationSeverity getSeverity() {
        return ValidationSeverity.ERROR;
    }
}
