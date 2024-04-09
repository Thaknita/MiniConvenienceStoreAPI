package com.springboot.minimartapi.user;

import com.springboot.minimartapi.product.Product;
import com.springboot.minimartapi.product.ProductRepo;
import com.springboot.minimartapi.user.address.AddressCreationDto;
import com.springboot.minimartapi.user.address.AddressEditionDto;
import com.springboot.minimartapi.user.cart.AddToCartDto;
import com.springboot.minimartapi.user.cart.Cart;
import com.springboot.minimartapi.user.cart.CartMapper;
import com.springboot.minimartapi.user.cart.CartRepo;
import com.springboot.minimartapi.user.payment.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PaymentRepo paymentRepo;
    private final PaymentMapper paymentMapper;
    private final CartRepo cartRepo;
    private final CartMapper cartMapper;
    private final ProductRepo productRepo;

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
    paymentMapper.fromPaymentEditionDto(paymentInformation, paymentEditionDto);
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

    @Override
    public void createAddress(AddressCreationDto addressCreationDto, Long userId) {
        User user = userRepo.findUserByUserId(userId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user invalid"));
        System.out.println(addressCreationDto.city());
        String address = addressCreationDto.city()+ " " + addressCreationDto.commune()+ " " + addressCreationDto.village()+ " " +addressCreationDto.street()+ " " +addressCreationDto.houseNumber();
        System.out.println(address);
        user.setDeliveryAddress(address);
        userRepo.save(user);
    }
    @Transactional
    @Override
    public void editAddressByUserId(Long userId, AddressEditionDto addressEditionDto) {
        String address = addressEditionDto.city()+" "+addressEditionDto.commune()+" "+ addressEditionDto.village()+" "+addressEditionDto.street()+ " "+addressEditionDto.houseNumber();
        userRepo.updateDeliveryAddressByUserId(address, userId);
    }

    @Override
    public void addProductToCart(AddToCartDto addToCartDto) {

        if (productRepo.existsById(addToCartDto.product().getId()) && (productRepo.getQty(addToCartDto.product().getId()) > 0)){
            if (cartRepo.existsByUserId(addToCartDto.userId())) {
                Cart carts = cartRepo.findByUserId(addToCartDto.userId());
                carts.getProducts().add(addToCartDto.product());
                cartRepo.save(carts);
            }
            else {
            List<Product> products = new ArrayList<>();

            Cart cart = cartMapper.fromAddToCartDto(addToCartDto);

            products.add(addToCartDto.product());

            cart.setProducts(products);

            cartRepo.save(cart); }
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product does not exist");
    }
}
