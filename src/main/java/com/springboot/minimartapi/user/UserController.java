package com.springboot.minimartapi.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    void createUser(@Valid @RequestBody UserCreationDto userCreationDto){

    }

    @PostMapping("/payments/create")
    void createPaymentInfo(@Valid @RequestBody PaymentCreationDto paymentCreationDto){
        userService.createPaymentInfo(paymentCreationDto);
    }



}
