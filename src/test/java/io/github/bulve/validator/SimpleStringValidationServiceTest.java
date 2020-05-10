package io.github.bulve.validator;

import io.github.bulve.validator.service.SimpleValidationService;
import io.github.bulve.validator.service.ValidationError;
import io.github.bulve.validator.service.ValidationResult;
import io.github.bulve.validator.service.ValidationService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class SimpleStringValidationServiceTest {

    private static ValidationService<String> stringValidationService;

    @BeforeClass
    public static void setUp() {
        stringValidationService = new SimpleValidationService<>(new TestStringRuleService());
    }

    @Test
    public void shouldSucceedWhenStringIsValid() {
        String stringToValidate = "simple string";
        ValidationResult validationResult = stringValidationService.validate(stringToValidate);
        assertThat(validationResult.isValid(), is(true));
        assertThat(validationResult.getValidationErrors(), hasSize(0));
    }

    @Test
    public void shouldFailWhenStringIsNull() {
        String stringToValidate = null;
        ValidationResult validationResult = stringValidationService.validate(stringToValidate);
        assertThat(validationResult.isValid(), is(false));
        assertThat(validationResult.getValidationErrors(), hasSize(2));
        List<String> errorMessages = validationResult.getValidationErrors().stream()
                .map(ValidationError::getValidationMessage)
                .collect(Collectors.toList());
        assertThat(errorMessages, containsInAnyOrder("String cannot be null", "Validation failure with null"));
    }

    @Test
    public void shouldFailWhenStringContainsDigitsAndSpecialSymbol() {
        String stringToValidate = "str with % and 09";
        ValidationResult validationResult = stringValidationService.validate(stringToValidate);
        assertThat(validationResult.isValid(), is(false));
        assertThat(validationResult.getValidationErrors(), hasSize(2));
        List<String> errorMessages = validationResult.getValidationErrors().stream()
                .map(ValidationError::getValidationMessage)
                .collect(Collectors.toList());
        assertThat(errorMessages, containsInAnyOrder("String cannot contain % symbol", "String cannot contain digits"));
    }
}
