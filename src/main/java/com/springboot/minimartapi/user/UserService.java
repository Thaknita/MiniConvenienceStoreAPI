package com.springboot.minimartapi.user;

import com.springboot.minimartapi.payment.PaymentCreationDto;
import com.springboot.minimartapi.payment.PaymentEditionDto;
import com.springboot.minimartapi.payment.PaymentInfoDto;

import java.util.Set;

public interface UserService {
    void createUser(UserCreationDto userCreationDto);
    void createPaymentInfo(PaymentCreationDto paymentCreationDto);

    Set<PaymentInfoDto> getInfoById(Long id);
    void editPayment(PaymentEditionDto paymentEditionDto, Long cardNum);
}
