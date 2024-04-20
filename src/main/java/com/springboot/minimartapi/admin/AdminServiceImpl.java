package com.springboot.minimartapi.admin;
import com.springboot.minimartapi.order.Order;
import com.springboot.minimartapi.order.OrderItemRepo;
import com.springboot.minimartapi.order.OrderMapper;
import com.springboot.minimartapi.order.OrderRepo;
import com.springboot.minimartapi.order.dto.AwaitToConfirmDto;
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
    public List<AwaitToConfirmDto> listOrderToConfirm() {
        return orderMapper.toAwaitToConfirmDtoList(orderRepo.findAllByOrderStatus("await to confirm"));
    }
    @Transactional
    @Override
    public Map<String, Object> confirmOrder(Long orderNumber) {
        orderRepo.confirmOrder(orderNumber);
        return Map.of("Order confirmed","await to deliver");
    }


}
