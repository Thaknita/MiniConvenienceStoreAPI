package com.springboot.minimartapi.product.category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
@Builder
public record CategoryCreationDto(
        @NotBlank
        String cateName
) {
}
