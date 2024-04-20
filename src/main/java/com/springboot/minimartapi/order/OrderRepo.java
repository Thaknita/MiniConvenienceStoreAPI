package com.springboot.minimartapi.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
List<Order> findAllByOrderStatus(String oderStatus);

}
