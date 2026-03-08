package io.github.ryosuke37.sylva.validator;


import io.github.ryosuke37.sylva.service.AuthService;
import io.github.ryosuke37.sylva.validator.annotation.UnregisteredHandle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UnregisteredHandleValidator implements ConstraintValidator<UnregisteredHandle, String> {

    final private AuthService authService;

    @Autowired
    UnregisteredHandleValidator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void initialize(UnregisteredHandle unregisteredHandle) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if(input == null || input.isBlank()){
            return true;
        }

        return !authService.existsByHandle(input);
    }
}