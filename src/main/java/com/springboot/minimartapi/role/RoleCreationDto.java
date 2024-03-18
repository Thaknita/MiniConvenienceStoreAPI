package com.springboot.minimartapi.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RoleCreationDto(
        @NotBlank
        String roleName

) {
}
