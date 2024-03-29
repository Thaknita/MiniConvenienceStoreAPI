package com.springboot.minimartapi.payment;

import lombok.Builder;

@Builder
public record PaymentEditionDto(
        String cardType,
        String dateMonthExp,
        Integer cvc

) {
}
