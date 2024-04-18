package com.springboot.minimartapi.user;


import com.springboot.minimartapi.order.Order;
import com.springboot.minimartapi.order.OrderDto;
import com.springboot.minimartapi.order.OrderMapper;
import com.springboot.minimartapi.order.OrderRepo;
import com.springboot.minimartapi.product.*;
import com.springboot.minimartapi.user.address.AddressCreationDto;
import com.springboot.minimartapi.user.address.AddressEditionDto;

import com.springboot.minimartapi.user.carts.*;
import com.springboot.minimartapi.user.payment.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;


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
    private final ProductRepo productRepo;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepo cartItemRepo;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;

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
    public void addProductToCart(CartItemDto cartItemDto, UserDto userId) {
      User user =  userMapper.fromUserDto(userId);

      if ( productRepo.qtyOnHand(cartItemDto.productId()) < cartItemDto.qty() ){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "insufficient qty in stock");
      }

        Product product = productRepo.findProductByProductId(cartItemDto.productId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not exist")
        );
      if (!cartRepo.existsByUserId(user)){
          Cart cart = new Cart();
          cart.setUserId(user);
          cartRepo.save(cart);
      }
      Cart cart = cartRepo.findCartByUserId(user);
      CartItem cartItem = cartItemMapper.fromCartItemDto(cartItemDto);
      cartItem.setReference(cart);
      cartItem.setProduct(product);
      cartItemRepo.save(cartItem);
    }
    @Override
    public List<ProductInCartDto> listProductInCart(UserDto userId) {
        User user = userMapper.fromUserDto(userId);
        Cart cart = cartRepo.findCartByUserId(user);

        List<Product> products = cartItemRepo.listCartItems(cart);

        List<ProductInCartDto> productInCartDtoList = new ArrayList<>();

        products.forEach(
                cartItem -> productInCartDtoList.add(
                           ProductInCartDto.builder()
                                   .productName(cartItemRepo.productName(cartItem))
                                   .price(cartItemRepo.price(cartItem))
                                   .qty(cartItemRepo.qty(cartItem))
                                   .build())
        );
        return productInCartDtoList;
    }
    @Transactional
    @Override
    public void deleteItemInCart(Long userId, ProductToRemoveFromCartDto productId) {
        UserDto userDto = UserDto.builder().userId(userId).build();
        User user = userMapper.fromUserDto(userDto);
        Cart cart = cartRepo.findCartByUserId(user);
        Product product = productMapper.toProduct(productId);
        cartItemRepo.deleteCartItemsByProductAndReference(product, cart);

    }
    @Transactional
    @Override
    public Map<String, Object> placeOrder(Long userId) {

        Cart cart = cartRepo.findCartByUserId(userMapper.fromUserDto(UserDto.builder().userId(userId).build()));

        if (cartItemRepo.totalPrice(cart) == null ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item in Cart");
        }
        if(userRepo.paymentInfo(userId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no payment method to purchase");
        }

        List<ProductInCartDto> productInCartDtoList = this.listProductInCart(UserDto.builder().userId(userId).build());
        OrderDto orderDto = OrderDto.builder()
               .orderStatus("await to confirm")
               .orderDate(LocalDateTime.now())
               .userId(UserDto.builder().userId(userId).build())
               .productList(productInCartDtoList)
               .totalPrice(cartItemRepo.totalPrice(cart))
               .vat((cartItemRepo.totalPrice(cart) * 0.1 ))
               .grandTotal((cartItemRepo.totalPrice(cart)+(cartItemRepo.totalPrice(cart) * 0.1 )  ))
               .build();

        Order order = orderMapper.toOrder(orderDto);

        orderRepo.save(order);

        cartItemRepo.cleanCart(cart);

        return Map.of("Order placed await to confirm", orderDto  );

    }

}

