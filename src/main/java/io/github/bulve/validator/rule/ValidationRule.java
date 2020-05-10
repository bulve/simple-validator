package io.github.bulve.validator.rule;

/**
 * Defines validation rule that will be used to validate instance T.
 *
 * @param <T> generic instance type to validate.
 *
 * @author Aleksandras Vasiliauskas
 */
@FunctionalInterface
public interface ValidationRule<T> {

    /**
     * Validates T and produces validation result.
     *
     * @param t instance to validate.
     * @return RuleValidationResult.
     */
    RuleValidationResult validate(T t);

}
