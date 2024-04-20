package com.springboot.minimartapi.product.dto;

import lombok.Builder;

@Builder
public record ProductIdDto(
        Long productId
) {
}
