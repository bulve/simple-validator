package io.github.bulve.validator.rule;

/**
 * @author Aleksandras Vasiliauskas
 */
public class RuleValidationResultImpl implements RuleValidationResult {

    private boolean valid;

    private String message;

    private ValidationSeverity severity;

    private RuleValidationResultImpl() { }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ValidationSeverity getSeverity() {
        return severity != null ? severity : RuleValidationResult.super.getSeverity();
    }

    public static RuleValidationResult failureResult(String message) {
        RuleValidationResultImpl result = new RuleValidationResultImpl();
        result.valid = false;
        result.message = message;
        return result;
    }

    public static RuleValidationResult failureResult(String message, ValidationSeverity validationSeverity) {
        RuleValidationResultImpl result = new RuleValidationResultImpl();
        result.valid = false;
        result.severity = validationSeverity;
        result.message = message;
        return result;
    }

    public static RuleValidationResult successResult() {
        RuleValidationResultImpl result = new RuleValidationResultImpl();
        result.valid = true;
        result.message = "";
        return result;
    }

    public static RuleValidationResult successResult(String message) {
        RuleValidationResultImpl result = new RuleValidationResultImpl();
        result.valid = true;
        result.message = message;
        return result;
    }

    public static RuleValidationResult successResult(String message, ValidationSeverity validationSeverity) {
        RuleValidationResultImpl result = new RuleValidationResultImpl();
        result.valid = true;
        result.severity = validationSeverity;
        result.message = message;
        return result;
    }
}
