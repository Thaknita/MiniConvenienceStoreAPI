package com.springboot.minimartapi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
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
    public PaymentDto listAllPaymentInfo(Long id) {


        return null;
    }
}
