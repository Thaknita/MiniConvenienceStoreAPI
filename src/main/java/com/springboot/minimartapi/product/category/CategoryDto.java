package com.springboot.minimartapi.product.category;

import lombok.Builder;

@Builder
public record CategoryDto(
        Integer cateId
) {
}
