package com.springboot.minimartapi.user.address;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(

        String city,

        String commune,


        String village,


        String street,

        String houseNumber
) {
}
