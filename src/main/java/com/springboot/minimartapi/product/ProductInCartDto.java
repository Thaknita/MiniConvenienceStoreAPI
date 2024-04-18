package com.springboot.minimartapi.product;

import lombok.Builder;

@Builder
public record ProductInCartDto(
        Long productId,
        String productName,
        Float price,
        Long qty
) {
}
