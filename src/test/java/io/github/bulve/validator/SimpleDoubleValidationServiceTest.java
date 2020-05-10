package io.github.bulve.validator;

import io.github.bulve.validator.rule.RuleService;
import io.github.bulve.validator.rule.ValidationSeverity;
import io.github.bulve.validator.service.SimpleValidationService;
import io.github.bulve.validator.service.ValidationError;
import io.github.bulve.validator.service.ValidationResult;
import io.github.bulve.validator.service.ValidationService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class SimpleDoubleValidationServiceTest {

    private ValidationService<Double> doubleValidationService;
    private RuleService<Double> doubleRuleService;

    @Before
    public void setUp() {
        doubleRuleService = new TestDoubleRuleService();
        doubleValidationService = new SimpleValidationService<>(doubleRuleService);
    }

    @Test
    public void shouldSucceedWhenDoubleIsValid() {
        Double doubleToValidate = 222D;
        ValidationResult validationResult = doubleValidationService.validate(doubleToValidate);
        assertThat(validationResult.isValid(), is(true));
        assertThat(validationResult.getValidationErrors(), hasSize(0));
    }

    @Test
    public void shouldFailWhenDoubleIsNull() {
        Double doubleToValidate = null;
        ValidationResult validationResult = doubleValidationService.validate(doubleToValidate);
        assertThat(validationResult.isValid(), is(false));
        assertThat(validationResult.getValidationErrors(), hasSize(2));
        List<String> errors = validationResult.getValidationErrors().stream()
                .map(ValidationError::getValidationMessage)
                .collect(Collectors.toList());
        assertThat(errors, containsInAnyOrder("Double cannot be null", "Validation failure with null"));
    }

    @Test
    public void shouldFailWhenDoubleIsNaN() {
        Double doubleToValidate = Double.NaN;
        ValidationResult validationResult = doubleValidationService.validate(doubleToValidate);
        assertThat(validationResult.isValid(), is(false));
        assertThat(validationResult.getValidationErrors(), hasSize(1));
        ValidationError error = validationResult.getValidationErrors().iterator().next();
        assertThat(error.getValidationMessage(), is("Double cannot be NaN"));
    }

    @Test
    public void shouldFailWhenDoubleIsInfinity() {
        Double doubleToValidate = Double.POSITIVE_INFINITY;
        ValidationResult validationResult = doubleValidationService.validate(doubleToValidate);
        assertThat(validationResult.isValid(), is(false));
        assertThat(validationResult.getValidationErrors(), hasSize(1));
        ValidationError error = validationResult.getValidationErrors().iterator().next();
        assertThat(error.getValidationMessage(), is("Double cannot be infinity"));
    }

    @Test
    public void shouldSucceedWhenHigherThan1000WithErrorSeverity() {
        Double doubleToValidate = 1001D;
        ValidationService<Double> validationService = new SimpleValidationService<>(doubleRuleService, ValidationSeverity.ERROR);
        ValidationResult validationResult = validationService.validate(doubleToValidate);
        assertThat(validationResult.isValid(), is(true));
        assertThat(validationResult.getValidationErrors(), hasSize(0));
    }

    @Test
    public void shouldFailWhenHigherThan1000WithWarningSeverity() {
        Double doubleToValidate = 1001D;
        ValidationService<Double> validationService = new SimpleValidationService<>(doubleRuleService, ValidationSeverity.WARNING);
        ValidationResult validationResult = validationService.validate(doubleToValidate);
        assertThat(validationResult.isValid(), is(false));
        assertThat(validationResult.getValidationErrors(), hasSize(1));
        ValidationError error = validationResult.getValidationErrors().iterator().next();
        assertThat(error.getValidationMessage(), is("Double should not be higher than 1000"));
    }
}
