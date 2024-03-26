package com.springboot.minimartapi.user;

import jakarta.validation.constraints.NotNull;

public record UserDto(
        @NotNull
        Long userId
) {
}
