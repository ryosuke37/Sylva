package io.github.ryosuke37.sylva.controller.form;

import io.github.ryosuke37.sylva.validator.annotation.NotBlankSpace;
import io.github.ryosuke37.sylva.validator.annotation.UuidV7;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
    @NotBlankSpace
    @Size(max = 255)
    private String content;

    @NotNull
    @UuidV7
    private String parentPostId;

    @NotNull
    @UuidV7
    private String quotedPostId;
}