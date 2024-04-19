package com.springboot.minimartapi.user.address.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(

        String city,

        String commune,


        String village,


        String street,

        String houseNumber
) {
}
