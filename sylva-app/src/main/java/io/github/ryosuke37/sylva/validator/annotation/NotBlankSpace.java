package io.github.ryosuke37.sylva.validator.annotation;

import io.github.ryosuke37.sylva.validator.NotBlankSpaceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = NotBlankSpaceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankSpace {
    String message() default "{validator.NotBlankSpace.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
