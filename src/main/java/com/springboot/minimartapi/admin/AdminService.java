package com.springboot.minimartapi.admin;

import com.springboot.minimartapi.order.dto.AwaitToConfirmDto;


import java.util.List;
import java.util.Map;

public interface AdminService {

    void deactivateUser(Long userId);
    void deleteUser(Long userId);
    List<AwaitToConfirmDto> listOrderToConfirm();
    Map<String , Object> confirmOrder(Long orderNumber);

}
