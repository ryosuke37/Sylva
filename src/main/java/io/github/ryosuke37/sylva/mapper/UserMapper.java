package io.github.ryosuke37.sylva.mapper;

import io.github.ryosuke37.sylva.controller.dto.UserDto;
import io.github.ryosuke37.sylva.repository.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity entity);
}
