package io.github.ryosuke37.sylva.validator.annotation;

import io.github.ryosuke37.sylva.validator.UuidV7Validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UuidV7Validator.class)
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UuidV7 {
    String message() default "{validator.UuidV7.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
