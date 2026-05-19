package io.github.ryosuke37.sylva.validator;

import io.github.ryosuke37.sylva.validator.annotation.UuidV7;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UuidV7Validator implements ConstraintValidator<UuidV7, String> {

    @Override
    public void initialize(UuidV7 uuidV7) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if(input == null || input.isBlank()){
            return true;
        }

        final String UUID_V7_REGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-7[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
        return input.matches(UUID_V7_REGEX);
    }
}
