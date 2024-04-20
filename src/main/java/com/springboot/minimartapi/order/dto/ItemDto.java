package com.springboot.minimartapi.order.dto;
import lombok.Builder;
@Builder
public record ItemDto(
        Long productId,
        String productName,
        Double price

) {
}
