package com.springboot.minimartapi.user.payment.dto;

import com.springboot.minimartapi.user.UserDto;
import lombok.Builder;

@Builder
public record PaymentInfoDto(
        Long cardNumber,
        String cardType,
        UserDto user

) {
}
