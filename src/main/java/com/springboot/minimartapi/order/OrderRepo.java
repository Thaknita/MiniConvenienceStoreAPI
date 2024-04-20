package com.springboot.minimartapi.order;

import com.springboot.minimartapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
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

@Query("""
    SELECT o.userId from Order as o
    WHERE o.orderNumber =?1
""")
User getUser(Long orderNumber);

@Modifying
@Query("""
UPDATE Order o
SET o.orderStatus ="delivering"
WHERE o.orderNumber =?1
""")
void deliver(Long orderNumber);
@Modifying
@Query("""
UPDATE Order o
SET o.orderStatus = "delivered"
WHERE o.orderNumber =?1
""")
void delivered(Long orderNumber);
@Modifying
@Query("""
UPDATE Order o
SET o.receivedDate = ?2
WHERE o.orderNumber =?1
""")
void updateReceiveDate(Long orderNumber, LocalDateTime date);
@Modifying
@Query("""
UPDATE Order o
SET o.isDelivered = true
WHERE o.orderNumber =?1
""")
void setDeliveredToTrue(Long orderNumber);







}
