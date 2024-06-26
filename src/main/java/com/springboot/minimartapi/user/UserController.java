package com.springboot.minimartapi.user;
import com.springboot.minimartapi.product.dto.ProductInCartDto;
import com.springboot.minimartapi.product.dto.ProductToRemoveFromCartDto;
import com.springboot.minimartapi.user.carts.CartItemDto;
import com.springboot.minimartapi.user.payment.dto.PaymentCreationDto;
import com.springboot.minimartapi.user.payment.dto.PaymentEditionDto;
import com.springboot.minimartapi.user.payment.dto.PaymentInfoDto;
import com.springboot.minimartapi.user.address.dto.AddressCreationDto;
import com.springboot.minimartapi.user.address.dto.AddressEditionDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    void createUser(@Valid @RequestBody UserCreationDto userCreationDto) throws MessagingException {
        userService.createUser(userCreationDto);
    }
    @PostMapping("/payments/create")
    void createPaymentInfo(@Valid @RequestBody PaymentCreationDto paymentCreationDto){
        userService.createPaymentInfo(paymentCreationDto);
    }
    @GetMapping("/payments/{id}")
    Set<PaymentInfoDto> findById(@PathVariable Long id){
       return userService.getInfoById(id);
    }

    @PatchMapping("/payments/update/{cardNum}")
     void editPayment(@PathVariable Long cardNum, @RequestBody PaymentEditionDto paymentEditionDto){
        userService.editPayment(paymentEditionDto, cardNum);
    }
    @DeleteMapping("/payments/delete/{cardNum}")
    void deletePaymentInfo(@PathVariable Long cardNum){
        userService.deletePaymentByCard(cardNum);
    }

    @PostMapping("/address/create/{userId}")
    void createDeliveryAddress(@Valid @RequestBody AddressCreationDto addressCreationDto, @PathVariable Long userId){
        userService.createAddress(addressCreationDto, userId);
    }

    @PatchMapping("/address/edit/{userId}")
    void editDeliveryAddress(@PathVariable Long userId, @RequestBody AddressEditionDto addressEditionDto ){
        userService.editAddressByUserId(userId, addressEditionDto);
    }

    @PostMapping("/carts/add/{userId}")
    void addProductToCart(@RequestBody CartItemDto product, @PathVariable Long userId){
        userService.addProductToCart(product,UserDto.builder().userId(userId).build());
    }

    @GetMapping("/carts/{userId}")
    List<ProductInCartDto> listProductInCart(@PathVariable Long userId){
        return userService.listProductInCart(UserDto.builder().userId(userId).build());
    }

    @DeleteMapping("/carts/delete/{userId}")
    void deleteProductFromCart(@PathVariable Long userId, @RequestBody ProductToRemoveFromCartDto productId){
        userService.deleteItemInCart(userId, productId);
    }

    @PostMapping("/orders/place/{userId}")
    Map<String, Object> placeOrder(@PathVariable Long userId){
        return userService.placeOrder(userId);
    }







}
