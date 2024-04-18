package com.springboot.minimartapi.admin.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RoleCreationDto(
        @NotBlank
        String roleName

) {
}
