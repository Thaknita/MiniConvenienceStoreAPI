package com.springboot.minimartapi.admin;
import com.springboot.minimartapi.order.*;
import com.springboot.minimartapi.order.dto.AdminOrderDto;
import com.springboot.minimartapi.order.dto.OrderDto;
import com.springboot.minimartapi.order.dto.OrderItemDto;
import com.springboot.minimartapi.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderMapper orderMapper;
    @Transactional
    @Override
    public void deactivateUser(Long userId) {
        userRepo.deactivateUser(userId);
    }
    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteByUserId(userId);
    }

    @Override
    public List<AdminOrderDto> listOrderToConfirm() {
        return orderMapper.toAwaitToConfirmDtoList(orderRepo.findAllByOrderStatus("await to confirm"));
    }
    @Transactional
    @Override
    public Map<String, Object> confirmOrder(Long orderNumber) {
        orderRepo.confirmOrder(orderNumber);
        return Map.of("Order confirmed","await to deliver");
    }

    @Override
    public List<AdminOrderDto> listOrderToDeliver() {
        return orderMapper.toAwaitToConfirmDtoList(orderRepo.findAllByOrderStatus("await to deliver"));
    }

    @Override
    public List<OrderItemDto> listOrderItemByOrderNumber(Long orderNumber) {
        Order order = orderMapper.toOrder(OrderDto.builder().orderNumber(orderNumber).build());
         List<OrderItem> orderItems = orderItemRepo.findOrderItemsByOrder(order);
         return orderMapper.fromOrderItemList(orderItems);
    }


}
