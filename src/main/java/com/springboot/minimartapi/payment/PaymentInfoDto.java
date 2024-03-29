package com.springboot.minimartapi.payment;

import com.springboot.minimartapi.user.UserDto;
import lombok.Builder;

@Builder
public record PaymentInfoDto(
        Long cardNumber,
        String cardType,
        UserDto user

) {
}
