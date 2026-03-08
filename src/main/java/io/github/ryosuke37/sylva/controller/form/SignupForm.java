package io.github.ryosuke37.sylva.controller.form;

import io.github.ryosuke37.sylva.validator.annotation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {
    @Handle
    @UnregisteredHandle
    @NotBlankSpace
    @Size(max=50)
    private String handle;

    @NotBlankSpace
    @Size(max=255)
    private String nickname;

    @Email
    @UnregisteredEmailAddress
    @NotBlankSpace
    @Size(max=255)
    private String emailAddress;

    @Password
    @Size(min=8, max=16)
    private String rawPassword;
}