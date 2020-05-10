package io.github.bulve.validator;

import io.github.bulve.validator.rule.RuleService;
import io.github.bulve.validator.rule.RuleValidationResultImpl;
import io.github.bulve.validator.rule.ValidationRule;
import io.github.bulve.validator.rule.ValidationSeverity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class TestDoubleRuleService implements RuleService<Double> {

    private Collection<ValidationRule<Double>> validationRules;

    public TestDoubleRuleService() {
        this.validationRules = Arrays.asList(
                doubleToValidate -> Objects.isNull(doubleToValidate)
                        ? RuleValidationResultImpl.failureResult("Double cannot be null")
                        : RuleValidationResultImpl.successResult(),
                doubleToValidate -> Double.isNaN(doubleToValidate)
                        ? RuleValidationResultImpl.failureResult("Double cannot be NaN")
                        : RuleValidationResultImpl.successResult(),
                doubleToValidate -> Double.isInfinite(doubleToValidate)
                        ? RuleValidationResultImpl.failureResult("Double cannot be infinity")
                        : RuleValidationResultImpl.successResult(),
                doubleToValidate -> doubleToValidate > 1000
                        ? RuleValidationResultImpl.failureResult("Double should not be higher than 1000", ValidationSeverity.WARNING)
                        : RuleValidationResultImpl.successResult(),
                doubleToValidate -> doubleToValidate < 0
                        ? RuleValidationResultImpl.failureResult("Double should not be lower than 0", ValidationSeverity.WARNING)
                        : RuleValidationResultImpl.successResult()
        );

    }

    @Override
    public Collection<ValidationRule<Double>> getValidationRules() {
        return validationRules;
    }
}
