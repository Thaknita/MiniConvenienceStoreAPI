package com.springboot.minimartapi.user;

import com.springboot.minimartapi.product.Product;
import com.springboot.minimartapi.user.payment.PaymentCreationDto;
import com.springboot.minimartapi.user.payment.PaymentEditionDto;
import com.springboot.minimartapi.user.payment.PaymentInfoDto;
import com.springboot.minimartapi.user.address.AddressCreationDto;
import com.springboot.minimartapi.user.address.AddressEditionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    void createUser(@Valid @RequestBody UserCreationDto userCreationDto){
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


}
