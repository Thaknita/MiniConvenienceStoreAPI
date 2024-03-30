package com.springboot.minimartapi.product;

import lombok.Builder;

@Builder
public record ProductDto(
      Long id,
      String productName,
      String productDescription,
      Long categoryId,
      Float price,
      Long qtyOnHand
) {
}
