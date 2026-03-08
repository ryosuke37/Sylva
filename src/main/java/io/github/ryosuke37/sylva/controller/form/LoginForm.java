package io.github.ryosuke37.sylva.controller.form;

import io.github.ryosuke37.sylva.validator.annotation.NotBlankSpace;
import io.github.ryosuke37.sylva.validator.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class LoginForm {
    @Email
    @NotBlankSpace
    private String emailAddress;

    @Password
    @Size(min=8, max=16)
    private String rawPassword;
}