package com.springboot.minimartapi.order.dto;
import com.springboot.minimartapi.user.UserDto;
import lombok.Builder;
import java.time.LocalDateTime;


@Builder
public record AdminOrderDto(
        Double totalPrice,
        Double vat,
        Double grandTotal,
        UserDto userId,
        String orderStatus,
        LocalDateTime orderDate

) {
}
