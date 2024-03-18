package com.springboot.minimartapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDto(
        @NotBlank
        @Email
        String emailAddress,
        @NotBlank
        String password

) {
}
