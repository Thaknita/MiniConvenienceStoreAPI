package com.springboot.minimartapi.product.dto;

import jakarta.validation.constraints.NotNull;

public record ProductToAddDto(

        @NotNull
        Long productId,
        @NotNull
        Integer qty
) {
}
