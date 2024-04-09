package com.springboot.minimartapi.user.cart;

import com.springboot.minimartapi.product.Product;
import com.springboot.minimartapi.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record AddToCartDto(
        @NotNull
        User userId,
        @NotNull
        Product product
) {
}
