package com.springboot.minimartapi.admin;

import com.springboot.minimartapi.order.Order;
import com.springboot.minimartapi.order.dto.AwaitToConfirmDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminService {

    void deactivateUser(Long userId);
    void deleteUser(Long userId);

    List<AwaitToConfirmDto> listOrderToConfirm();

}
