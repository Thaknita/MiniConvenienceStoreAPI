package com.springboot.minimartapi.user;

import lombok.Builder;

@Builder
public record PaymentInfoDto(
        Long cardNumber,
        String cardType,
        UserDto user

) {
}
