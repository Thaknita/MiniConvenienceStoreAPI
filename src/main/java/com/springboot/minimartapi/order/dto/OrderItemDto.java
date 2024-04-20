package com.springboot.minimartapi.order.dto;
import lombok.Builder;

@Builder
public record OrderItemDto(
        ItemDto product,
        Long qty

) {
}
