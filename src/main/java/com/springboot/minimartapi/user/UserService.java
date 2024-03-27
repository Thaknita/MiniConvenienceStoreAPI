package com.springboot.minimartapi.user;

import java.util.Set;

public interface UserService {
    void createUser(UserCreationDto userCreationDto);
    void createPaymentInfo(PaymentCreationDto paymentCreationDto);

    Set<PaymentInfoDto> getInfoById(Long id);
}
