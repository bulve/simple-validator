package io.github.bulve.validator;

import io.github.bulve.validator.service.SimpleValidationService;
import io.github.bulve.validator.service.ValidationError;
import io.github.bulve.validator.service.ValidationResult;
import io.github.bulve.validator.service.ValidationServiceHelper;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class ValidationServiceHelperTests {

    @Test
    public void shouldInvokeAllValidators() {
        ValidationResult result = ValidationServiceHelper.validator()
                .with("12323", new SimpleValidationService<>(new TestStringRuleService()))
                .with(Double.NaN, new SimpleValidationService<>(new TestDoubleRuleService()))
                .validate();

        assertThat(result.isValid(), is(false));
        List<String> validationMessages = result.getValidationErrors().stream()
                .map(ValidationError::getValidationMessage)
                .collect(Collectors.toList());
        assertThat(validationMessages, hasSize(2));
        assertThat(validationMessages, containsInAnyOrder("String cannot contain digits", "Double cannot be NaN"));
    }

    @Test
    public void shouldSuccessWhenEverythingIsValid() {
        ValidationResult result = ValidationServiceHelper.validator()
                .with("normal string", new SimpleValidationService<>(new TestStringRuleService()))
                .with(123D, new SimpleValidationService<>(new TestDoubleRuleService()))
                .validate();

        assertThat(result.isValid(), is(true));
        assertThat(result.getValidationErrors(), hasSize(0));
    }
}
