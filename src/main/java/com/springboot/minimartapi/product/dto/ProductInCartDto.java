package com.springboot.minimartapi.product.dto;

import com.springboot.minimartapi.order.dto.ItemDto;
import lombok.Builder;

@Builder
public record ProductInCartDto(
        ItemDto product,
        Long qty
) {
}
