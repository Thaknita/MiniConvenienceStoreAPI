package com.springboot.minimartapi.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProductCreationDto(
    @NotBlank
    String productName,
    @NotBlank
    String productDescription,
    @NotNull
    CategoryDto category,
    @NotNull
    Double price,
    @NotNull
    Long qtyOnHand

) {
}
