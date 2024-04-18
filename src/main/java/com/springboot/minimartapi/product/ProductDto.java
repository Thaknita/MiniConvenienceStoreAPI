package com.springboot.minimartapi.product;

import lombok.Builder;

@Builder
public record ProductDto(
      Long id,
      String productName,
      String productDescription,
      Integer categoryId,
      Double price,
      Long qtyOnHand
) {
}
