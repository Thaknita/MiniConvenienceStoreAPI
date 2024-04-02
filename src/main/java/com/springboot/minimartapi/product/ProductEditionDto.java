package com.springboot.minimartapi.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProductEditionDto(


        String productName,

        String productDescription,

        Float price,

        Category category

) {
}
