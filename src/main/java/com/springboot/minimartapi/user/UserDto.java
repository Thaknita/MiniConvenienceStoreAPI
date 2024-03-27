package com.springboot.minimartapi.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDto(
        Long userId
) {
}
