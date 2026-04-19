package io.github.ryosuke37.sylva.controller.dto;

import lombok.Value;
import lombok.With;

import java.util.List;

@Value
@With
public class PostTreeDto {
    List<PostDto> ancestors;
    PostDto target;
    List<PostDto> descendants;
}
