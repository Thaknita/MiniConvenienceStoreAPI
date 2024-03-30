package com.springboot.minimartapi.user.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressCreationDto(
        @NotBlank
        String city,
        @NotBlank
        String commune,
        @NotBlank

        String village,
        @NotBlank

        String street,
        @NotBlank
        String houseNumber


) {
}
