package com.springboot.minimartapi.product.dto;

import lombok.Builder;

@Builder
public record ProductInCartDto(
        Long productId,
        String productName,
        Float price,
        Long qty
) {
}
