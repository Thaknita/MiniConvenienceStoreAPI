package com.springboot.minimartapi.user;

import com.springboot.minimartapi.payment.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PaymentRepo paymentRepo;
    private final PaymentMapper paymentMapper;
    @Override
    public void createUser(UserCreationDto userCreationDto) {

        User users = userMapper.fromUserCreationDto(userCreationDto);

        if (userRepo.existsByUserName(users.getUserName())){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Username is taken");
        }
        if (userRepo.existsByEmailAddress(users.getEmailAddress())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Address is conflict");
        }
        users.setIsActive(true);
        users.setIsVerified(false);
        users.setPassword( passwordEncoder.encode(userCreationDto.password()) );

        userRepo.save(users);

    }
    @Override
    public void createPaymentInfo(PaymentCreationDto paymentCreationDto) {

       if (paymentRepo.existsByCardNumber(paymentCreationDto.cardNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "card is already been used");
        }



        PaymentInformation paymentInformation = paymentMapper.fromPaymentCreationDto(paymentCreationDto);

        Set<PaymentInformation> paymentInformations = new HashSet<>();

        paymentInformations.add(paymentInformation);

        // Update user to reference the newly created payment information
        User user = userRepo.findUserByUserId(paymentCreationDto.user().getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + paymentCreationDto.user().getUserId()+ " not found"));
        user.setPaymentInformation(paymentInformations);
        userRepo.save(user);

        paymentRepo.save(paymentInformation);

    }

    @Override
    public Set<PaymentInfoDto>  getInfoById(Long id) {

       return paymentMapper.fromPaymentInformation(paymentRepo.findByUserId(id));
    }
    @Override
    public void editPayment(PaymentEditionDto paymentEditionDto, Long cardNum) {

    PaymentInformation paymentInformation = paymentRepo.findPaymentInformationByCardNumber(cardNum)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

    System.out.println("Before map " + paymentInformation.getCardType());
    paymentMapper.fromPaymentEditionDto(paymentInformation, paymentEditionDto);
    System.out.println(("after map " + paymentInformation.getCardType()));


    paymentRepo.save(paymentInformation);



    }
    @Transactional
    @Override
    public void deletePaymentByCard(Long cardNum) {
        if (!paymentRepo.existsByCardNumber(cardNum)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card doesn't exist");
        }

        paymentRepo.deleteByCardNumber(cardNum);


    }


}
