package com.springboot.minimartapi.product.dto;
import com.springboot.minimartapi.product.category.Category;
import lombok.Builder;

@Builder
public record ProductEditionDto(


        String productName,

        String productDescription,

        Double price,

        Category category

) {
}
