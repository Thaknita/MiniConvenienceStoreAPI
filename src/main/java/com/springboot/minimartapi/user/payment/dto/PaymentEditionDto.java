package com.springboot.minimartapi.user.payment.dto;

import lombok.Builder;

@Builder
public record PaymentEditionDto(
        String cardType,
        String dateMonthExp,
        Integer cvc

) {
}
