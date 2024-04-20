package com.springboot.minimartapi.admin;
import com.springboot.minimartapi.order.*;
import com.springboot.minimartapi.order.dto.AdminOrderDto;
import com.springboot.minimartapi.order.dto.OrderDto;
import com.springboot.minimartapi.order.dto.OrderItemDto;
import com.springboot.minimartapi.product.ProductMapper;
import com.springboot.minimartapi.product.ProductRepo;
import com.springboot.minimartapi.transaction.*;
import com.springboot.minimartapi.user.User;
import com.springboot.minimartapi.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderMapper orderMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionRepo transactionRepo;
    private final ProductMapper productMapper;
    private final ProductRepo productRepo;
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

    @Override
    public String getDeliveryAddress(Long orderNumber) {
        User user = orderRepo.getUser(orderNumber);
        return userRepo.getAddress(user.getUserId());
    }

    @Transactional
    @Override
    public Map<String, Object> deliver(Long orderNumber) {
        orderRepo.deliver(orderNumber);
        return Map.of("order is on the way", orderNumber);
    }

    @Override
    public List<AdminOrderDto> listDeliveringOrder() {
        List<Order> orders = orderRepo.findAllByOrderStatus("delivering");
        return orderMapper.toAwaitToConfirmDtoList(orders);
    }
    @Transactional
    @Override
    public Map<String, Object> orderConfirmReceived(Long orderNumber) {
        orderRepo.delivered(orderNumber);
        orderRepo.setDeliveredToTrue(orderNumber);
        orderRepo.updateReceiveDate(orderNumber, LocalDateTime.now());
        return Map.of("order Completed", orderNumber);
    }
    @Transactional
    @Override
    public void bookProductInToStock(TransactionCreationDto transactionCreationDto) {
        if (!productRepo.existsById(transactionCreationDto.productId().productId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product doesn't exist, please create product first");
        }
        Transaction transaction =  transactionMapper.toTransaction(transactionCreationDto);
        transaction.setTransactionType("IN");
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepo.save(transaction);
        productRepo.updateQty(transaction.getProductId().getProductId(), productRepo.qtyOnHand(transaction.getProductId().getProductId() )+ transaction.getProductQty());
    }
    @Override
    public void cutProductFromStock(TransactionCreationDto transactionCreationDto) {
        if (!productRepo.existsById(transactionCreationDto.productId().productId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product doesn't exist, please create product first");
        }
        Transaction transaction = transactionMapper.toTransaction(transactionCreationDto);
        transaction.setTransactionType("OUT");
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepo.save(transaction);
        productRepo.updateQty(transaction.getProductId().getProductId(), productRepo.qtyOnHand(transaction.getProductId().getProductId() )+ transaction.getProductQty());
    }

    @Override
    public List<TransactionDto> transactionDtoList() {
       List<Transaction> transactions = transactionRepo.findAll();
       return transactionMapper.toTransactionDto(transactions);
    }
    @Override
    public Long checkQtyOnHandOfProductId(Long productId) {
        return productRepo.qtyOnHand(productId);
    }
}
