package com.springboot.minimartapi.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
List<OrderItem> findOrderItemsByOrder(Order order);


}
