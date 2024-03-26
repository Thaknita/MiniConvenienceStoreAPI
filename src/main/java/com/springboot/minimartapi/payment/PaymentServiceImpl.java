/*package com.springboot.minimartapi.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;

    private final PaymentMapper paymentMapper;
    @Override
    public void  createPayment(PaymentCreationDto paymentCreationDto) {

            PaymentInformation paymentInformation = paymentMapper.fromPaymentCreationDto(paymentCreationDto);

            if (paymentRepo.existsByCardNumber(paymentCreationDto.cardNumber())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Card already been used");
            }

            paymentRepo.save(paymentInformation);
    }
} */
