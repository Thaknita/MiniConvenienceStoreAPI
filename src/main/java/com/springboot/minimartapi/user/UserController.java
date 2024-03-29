package com.springboot.minimartapi.user;

import com.springboot.minimartapi.payment.PaymentCreationDto;
import com.springboot.minimartapi.payment.PaymentEditionDto;
import com.springboot.minimartapi.payment.PaymentInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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

    @PutMapping("/payments/update/{cardNum}")
     void editPayment(@PathVariable Long cardNum, @RequestBody PaymentEditionDto paymentEditionDto){
        userService.editPayment(paymentEditionDto, cardNum);
    }
    @DeleteMapping("/payments/delete/{cardNum}")
    void deletePaymentInfo(@PathVariable Long cardNum){
        userService.deletePaymentByCard(cardNum);
    }





}
