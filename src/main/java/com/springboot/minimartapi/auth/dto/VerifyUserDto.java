package com.springboot.minimartapi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyUserDto(
        @NotBlank
        @Email
        String emailAddress,
        @NotBlank
        String verifyCode
) {
}
