package com.springboot.minimartapi.user.payment;

import com.springboot.minimartapi.user.UserDto;
import lombok.Builder;

@Builder
public record PaymentInfoDto(
        Long cardNumber,
        String cardType,
        UserDto user

) {
}
