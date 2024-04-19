package com.springboot.minimartapi.order;

import com.springboot.minimartapi.product.dto.ProductInCartDto;
import com.springboot.minimartapi.user.UserDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDto(
        Double totalPrice,
        Double vat,
        Double grandTotal,
        UserDto userId,
        String orderStatus,
        LocalDateTime orderDate,
        List<ProductInCartDto> productList
) {
}
