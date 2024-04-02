package com.springboot.minimartapi.product;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
@Builder
public record CategoryCreationDto(
        @NotBlank
        String cateName
) {
}
