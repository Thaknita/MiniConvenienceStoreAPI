package com.springboot.minimartapi.product;

import lombok.Builder;

@Builder
public record ProductInCartDto(
        String productName,
        Float price,
        Long qty
) {
}
