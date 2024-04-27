package com.springboot.minimartapi.user;
import com.springboot.minimartapi.admin.AdminService;
import com.springboot.minimartapi.order.*;
import com.springboot.minimartapi.order.dto.ItemDto;
import com.springboot.minimartapi.order.dto.OrderDto;
import com.springboot.minimartapi.order.dto.OrderItemDto;
import com.springboot.minimartapi.product.dto.ProductIdDto;
import com.springboot.minimartapi.role.Role;
import com.springboot.minimartapi.role.RoleDto;
import com.springboot.minimartapi.role.RoleMapper;
import com.springboot.minimartapi.product.*;
import com.springboot.minimartapi.product.dto.ProductInCartDto;
import com.springboot.minimartapi.product.dto.ProductToRemoveFromCartDto;
import com.springboot.minimartapi.transaction.TransactionCreationDto;
import com.springboot.minimartapi.user.address.dto.AddressCreationDto;
import com.springboot.minimartapi.user.address.dto.AddressEditionDto;
import com.springboot.minimartapi.user.carts.*;
import com.springboot.minimartapi.user.payment.*;
import com.springboot.minimartapi.user.payment.dto.PaymentCreationDto;
import com.springboot.minimartapi.user.payment.dto.PaymentEditionDto;
import com.springboot.minimartapi.user.payment.dto.PaymentInfoDto;
import com.springboot.minimartapi.util.RandomNumber;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    private final RoleMapper roleMapper;
    private final OrderItemRepo orderItemRepo;
    private final AdminService adminService;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String adminMail;

    @Override
    public void createUser(UserCreationDto userCreationDto) throws MessagingException {

        User users = userMapper.fromUserCreationDto(userCreationDto);

        if (userRepo.existsByUserName(users.getUserName())){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Username is taken");
        }
        if (userRepo.existsByEmailAddress(users.getEmailAddress())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Address is conflict");
        }
        String verifyNumber = RandomNumber.getSixDigit();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setSubject("Account Verification");
        mimeMessageHelper.setText(verifyNumber);
        mimeMessageHelper.setTo(userCreationDto.emailAddress());
        mimeMessageHelper.setFrom(adminMail);

        //Send Message
        javaMailSender.send(mimeMessage);


        Set<Role> roles = new HashSet<>();
        roles.add(roleMapper.fromRoleDto(RoleDto.builder().id(1).build()));

        users.setRoles(roles);
        users.setIsActive(true);
        users.setVerifyCode(verifyNumber);
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
                                   .product(ItemDto.builder()
                                           .productId(cartItem.getProductId())
                                           .productName(cartItemRepo.productName(cartItem))
                                           .price(cartItemRepo.price(cartItem))
                                           .build())
                                   .qty(cartItemRepo.qty(cartItem, cart))
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

        List<OrderItemDto> orderItemDtoList = orderMapper.toOrderItemDtoList(productInCartDtoList);

        OrderDto orderDto = OrderDto.builder()
               .orderStatus("await to confirm")
               .orderDate(LocalDateTime.now())
               .userId(UserDto.builder().userId(userId).build())
               .orderItemDtoList(orderItemDtoList)
               .totalPrice(cartItemRepo.totalPrice(cart))
               .vat((cartItemRepo.totalPrice(cart) * 0.1 ))
               .grandTotal((cartItemRepo.totalPrice(cart)+(cartItemRepo.totalPrice(cart) * 0.1 )  ))
               .build();

        orderDto.orderItemDtoList().forEach(
                product -> {
                   if ( product.qty() > productRepo.qtyOnHand(product.product().productId()) ){
                       System.out.println(product.product().productId()+ "productId");
                       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product out of stock");
                   }
                   else{
                       TransactionCreationDto transactionCreationDto = TransactionCreationDto.builder()
                               .productQty(-product.qty())
                               .productId(ProductIdDto.builder().productId(product.product().productId()).build())
                               .build();
                       adminService.cutProductFromStock(transactionCreationDto);
                   }
                }
        );
        List<OrderItem> orderItems =  orderMapper.fromOrderItemDto(orderItemDtoList);
        Order order = orderMapper.toOrder(orderDto);
        order.setIsDelivered(false);
        orderRepo.save(order);
        orderItems.forEach(
                orderItem1 -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(orderItem1.getProduct());
                    orderItem.setQty(orderItem1.getQty());
                    orderItemRepo.save(orderItem);
                }
        );
        cartItemRepo.cleanCart(cart);
        return Map.of( "Order Reference: ", order.getOrderNumber(), "Order placed await to confirm", orderDto);

    }

}
