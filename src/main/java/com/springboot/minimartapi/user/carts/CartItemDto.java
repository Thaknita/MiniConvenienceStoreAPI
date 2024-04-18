package com.springboot.minimartapi.user.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartItemDto(
        @NotNull
        Long productId,
        @NotNull
        Long qty

) {
}
