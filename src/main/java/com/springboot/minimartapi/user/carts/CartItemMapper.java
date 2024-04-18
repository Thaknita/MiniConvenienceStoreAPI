package com.springboot.minimartapi.user.carts;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem fromCartItemDto(CartItemDto cartItemDto);
    Cart  fromCartDto(CartDto cartDto);
    List<CartItemDto> toCartItemDto(List<CartItem> cartItems);




}
