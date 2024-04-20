package com.springboot.minimartapi.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
List<Order> findAllByOrderStatus(String oderStatus);
@Modifying
@Query("""
    UPDATE Order o
    SET o.orderStatus ="await to deliver"
    WHERE o.orderNumber =?1
""")
void confirmOrder(Long orderNumber);


}
