package com.springboot.minimartapi.product;

import com.springboot.minimartapi.user.CartItemDto;
import lombok.Builder;

@Builder
public record ProductInCartDto(

        String productName,
        Float price,
        Long qty
) {
}
