package io.github.ryosuke37.sylva.controller.dto;

import lombok.Value;
import lombok.With;

import java.time.LocalDateTime;

@Value
@With
public class PostDto {
    String id;
    String content;
    UserDto user;
    PostDto rootPost;
    PostDto parentPost;
    PostDto quotedPost;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
}
