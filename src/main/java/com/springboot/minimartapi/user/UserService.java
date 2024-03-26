package com.springboot.minimartapi.user;

public interface UserService {
    void createUser(UserCreationDto userCreationDto);
    void createPaymentInfo(PaymentCreationDto paymentCreationDto);

    PaymentDto listAllPaymentInfo(Long id);


}
