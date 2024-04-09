package com.springboot.minimartapi.user;
import lombok.Builder;

@Builder
public record UserDto(
        Long userId
) {
}
