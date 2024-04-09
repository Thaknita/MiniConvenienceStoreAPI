package com.springboot.minimartapi.user;

import com.springboot.minimartapi.user.cart.AddToCartDto;
import com.springboot.minimartapi.user.payment.PaymentCreationDto;
import com.springboot.minimartapi.user.payment.PaymentEditionDto;
import com.springboot.minimartapi.user.payment.PaymentInfoDto;
import com.springboot.minimartapi.user.address.AddressCreationDto;
import com.springboot.minimartapi.user.address.AddressEditionDto;

import java.util.Map;
import java.util.Set;

public interface UserService {
    void createUser(UserCreationDto userCreationDto);
    void createPaymentInfo(PaymentCreationDto paymentCreationDto);

    Set<PaymentInfoDto> getInfoById(Long id);
    void editPayment(PaymentEditionDto paymentEditionDto, Long cardNum);

    void deletePaymentByCard(Long cardNum);

    void createAddress(AddressCreationDto addressCreationDto, Long userId);

    void editAddressByUserId(Long userId, AddressEditionDto addressEditionDto);

    void addProductToCart(AddToCartDto addToCartDto);
}
