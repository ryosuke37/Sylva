package io.github.ryosuke37.sylva.validator;


import io.github.ryosuke37.sylva.service.AuthService;
import io.github.ryosuke37.sylva.validator.annotation.UnregisteredEmailAddress;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UnregisteredEmailAddressValidator implements ConstraintValidator<UnregisteredEmailAddress, String> {

    final private AuthService authService;

    @Autowired
    UnregisteredEmailAddressValidator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void initialize(UnregisteredEmailAddress unregisteredEmailAddress) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if(input == null || input.isBlank()){
            return true;
        }

        return !authService.existsByEmail(input);
    }
}