package com.springboot.minimartapi.admin;

import com.springboot.minimartapi.order.OrderItem;
import com.springboot.minimartapi.order.dto.AdminOrderDto;
import com.springboot.minimartapi.order.dto.OrderItemDto;
import com.springboot.minimartapi.transaction.TransactionCreationDto;
import com.springboot.minimartapi.transaction.TransactionDto;


import java.util.List;
import java.util.Map;

public interface AdminService {

    void deactivateUser(Long userId);
    void deleteUser(Long userId);
    List<AdminOrderDto> listOrderToConfirm();
    Map<String , Object> confirmOrder(Long orderNumber);
    List<AdminOrderDto> listOrderToDeliver();
    List<OrderItemDto> listOrderItemByOrderNumber(Long orderNumber);
    String getDeliveryAddress(Long orderNumber);
    Map<String, Object> deliver(Long orderNumber);
    List<AdminOrderDto> listDeliveringOrder();

    Map<String, Object> orderConfirmReceived(Long orderNumber);

    void bookProductInToStock(TransactionCreationDto transactionCreationDto);

    void cutProductFromStock(TransactionCreationDto transactionCreationDto);

    List<TransactionDto> transactionDtoList();

    Long checkQtyOnHandOfProductId(Long productId);


}
