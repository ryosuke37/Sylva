package io.github.ryosuke37.sylva.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import io.github.ryosuke37.sylva.validator.HandleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Documented
@Constraint(validatedBy = HandleValidator.class)
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Handle {
    String message() default "{validator.Handle.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
