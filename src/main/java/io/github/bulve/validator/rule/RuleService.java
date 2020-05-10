package io.github.bulve.validator.rule;

import java.util.Collection;

/**
 * @author Aleksandras Vasiliauskas
 */
public interface RuleService<T> {

    Collection<ValidationRule<T>> getValidationRules();
}
