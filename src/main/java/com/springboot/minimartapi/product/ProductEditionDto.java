package com.springboot.minimartapi.product;
import lombok.Builder;

@Builder
public record ProductEditionDto(


        String productName,

        String productDescription,

        Double price,

        Category category

) {
}
