package com.springboot.minimartapi.order;

import com.springboot.minimartapi.order.dto.AdminOrderDto;
import com.springboot.minimartapi.order.dto.OrderDto;
import com.springboot.minimartapi.order.dto.OrderItemDto;
import com.springboot.minimartapi.product.dto.ProductInCartDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);
    List<OrderItemDto> toOrderItemDtoList(List<ProductInCartDto> productInCartDtoList);
    List<OrderItem> fromOrderItemDto(List<OrderItemDto> orderItemDtoList);
    List<AdminOrderDto> toAwaitToConfirmDtoList(List<Order> orders);



}


