package com.springboot.minimartapi.product.dto;

import com.springboot.minimartapi.product.category.CategoryDto;
import lombok.Builder;

@Builder
public record ProductDto(
      Long productId,
      String productName,
      String productDescription,
      CategoryDto category,
      Double price,
      Long qtyOnHand
) {
}
