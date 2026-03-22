package io.github.ryosuke37.sylva.validator;


import io.github.ryosuke37.sylva.validator.annotation.NotBlankSpace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankSpaceValidator implements ConstraintValidator<NotBlankSpace, String> {

    @Override
    public void initialize(NotBlankSpace notBlankSpace) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        return input != null && !input.isBlank();
    }
}