package com.springboot.minimartapi.admin.role;

import lombok.Builder;

@Builder
public record RoleEditionDto(
    String roleName
) {
}
