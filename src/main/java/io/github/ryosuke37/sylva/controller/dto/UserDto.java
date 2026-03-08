package io.github.ryosuke37.sylva.controller.dto;

import io.github.ryosuke37.sylva.common.enums.Country;
import io.github.ryosuke37.sylva.common.enums.Language;
import lombok.Data;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Value
@With
@Accessors(chain = true)
public class UserDto {
    String handle;
    String nickname;
    String emailAddress;
    Country country;
    Language language;
    String selfIntroduction;
    LocalDateTime createdDate;
}
