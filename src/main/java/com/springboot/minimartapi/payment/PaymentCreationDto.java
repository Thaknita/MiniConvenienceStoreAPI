package com.springboot.minimartapi.payment;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public record PaymentCreationDto(

    String cardNumber,
    String cardType,
    String dateMonth,
    Integer cvc

) {
}
