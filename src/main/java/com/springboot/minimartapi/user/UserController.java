package com.springboot.minimartapi.user;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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



}
