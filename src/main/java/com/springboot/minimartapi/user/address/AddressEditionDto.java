package com.springboot.minimartapi.user.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressEditionDto(
        String city,
        String commune,
        String village,
        String street,
        String houseNumber
) {
}
