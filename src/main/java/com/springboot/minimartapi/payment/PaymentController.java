package com.springboot.minimartapi.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @PostMapping("/create")
    void createPayment(PaymentCreationDto paymentCreationDto){

    }



}
