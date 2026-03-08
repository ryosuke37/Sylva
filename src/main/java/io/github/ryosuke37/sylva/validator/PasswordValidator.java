package io.github.ryosuke37.sylva.validator;


import io.github.ryosuke37.sylva.validator.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password password) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if(input == null || input.isBlank()){
            return true;
        }

        final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!-/:-@\\[-`{-~])[!-~]*$";
        return input.matches(PASSWORD_REGEX);
    }
}