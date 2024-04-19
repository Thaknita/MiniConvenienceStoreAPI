package com.springboot.minimartapi.user.payment.dto;
import com.springboot.minimartapi.user.User;
import com.springboot.minimartapi.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record PaymentCreationDto(
    @NotNull
    Long cardNumber,
    @NotBlank
    String cardType,
    @NotBlank
    String dateMonthExp,
    @NotNull
    Integer cvc,
    @NotNull
    User user

) {
}
