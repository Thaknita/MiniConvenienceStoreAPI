package com.springboot.minimartapi.user.cart;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart fromAddToCartDto (AddToCartDto addToCartDto);

}
