package io.github.ryosuke37.sylva.validator;


import io.github.ryosuke37.sylva.validator.annotation.Handle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HandleValidator implements ConstraintValidator<Handle, String> {

    @Override
    public void initialize(Handle handle) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if(input == null || input.isBlank()){
            return true;
        }

        final String HANDLE_REGEX = "^[a-zA-Z0-9_]*$";
        return input.matches(HANDLE_REGEX);
    }
}