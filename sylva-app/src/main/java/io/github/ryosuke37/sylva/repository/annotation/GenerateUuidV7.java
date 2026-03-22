package io.github.ryosuke37.sylva.repository.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.github.ryosuke37.sylva.repository.generator.UuidV7Generator;
import org.hibernate.annotations.IdGeneratorType;

@IdGeneratorType(UuidV7Generator.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface GenerateUuidV7 {
}