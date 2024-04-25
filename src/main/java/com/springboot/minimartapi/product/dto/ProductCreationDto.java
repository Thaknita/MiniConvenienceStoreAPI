package com.springboot.minimartapi.product.dto;

import com.springboot.minimartapi.product.category.CategoryDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ProductCreationDto(
    @NotBlank
    String productName,
    @NotBlank
    String productDescription,
    @NotNull
    CategoryDto category,
    @NotNull
    Double price

) {
}
