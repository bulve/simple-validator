package io.github.bulve.validator;

import io.github.bulve.validator.rule.RuleService;
import io.github.bulve.validator.rule.RuleValidationResultImpl;
import io.github.bulve.validator.rule.ValidationRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class TestStringRuleService implements RuleService<String> {

    private Collection<ValidationRule<String>> validationRules;

    public TestStringRuleService() {
        this.validationRules = Arrays.asList(
                stringToValidate -> Objects.isNull(stringToValidate)
                        ? RuleValidationResultImpl.failureResult("String cannot be null")
                        : RuleValidationResultImpl.successResult(),
                stringToValidate -> stringToValidate.length() == 0
                        ? RuleValidationResultImpl.failureResult("String length cannot be empty")
                        : RuleValidationResultImpl.successResult(),
                stringToValidate -> stringToValidate.contains("%")
                        ? RuleValidationResultImpl.failureResult("String cannot contain % symbol")
                        : RuleValidationResultImpl.successResult(),
                stringToValidate -> stringToValidate.chars().anyMatch(Character::isDigit)
                        ? RuleValidationResultImpl.failureResult("String cannot contain digits")
                        : RuleValidationResultImpl.successResult()
        );
    }

    @Override
    public Collection<ValidationRule<String>> getValidationRules() {
        return validationRules;
    }
}
