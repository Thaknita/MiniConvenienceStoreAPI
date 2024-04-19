package com.springboot.minimartapi.user;

import com.springboot.minimartapi.product.dto.ProductInCartDto;
import com.springboot.minimartapi.product.dto.ProductToRemoveFromCartDto;
import com.springboot.minimartapi.user.carts.CartItemDto;
import com.springboot.minimartapi.user.payment.dto.PaymentCreationDto;
import com.springboot.minimartapi.user.payment.dto.PaymentEditionDto;
import com.springboot.minimartapi.user.payment.dto.PaymentInfoDto;
import com.springboot.minimartapi.user.address.dto.AddressCreationDto;
import com.springboot.minimartapi.user.address.dto.AddressEditionDto;

import java.util.List;
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

    void addProductToCart(CartItemDto cartItemDto, UserDto userId);

    List<ProductInCartDto> listProductInCart(UserDto userId);

    void deleteItemInCart(Long userId, ProductToRemoveFromCartDto productId);

    Map<String, Object> placeOrder(Long userId);




}
