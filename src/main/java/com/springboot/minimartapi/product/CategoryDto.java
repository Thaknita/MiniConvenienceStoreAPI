package com.springboot.minimartapi.product;

import lombok.Builder;

@Builder
public record CategoryDto(
        Integer cateId
) {
}
