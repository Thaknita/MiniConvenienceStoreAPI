package com.springboot.minimartapi.admin;

import com.springboot.minimartapi.order.dto.AdminOrderDto;
import com.springboot.minimartapi.order.dto.OrderItemDto;
import com.springboot.minimartapi.product.ProductRepo;
import com.springboot.minimartapi.role.RoleCreationDto;
import com.springboot.minimartapi.role.RoleEditionDto;
import com.springboot.minimartapi.role.RoleService;
import com.springboot.minimartapi.product.category.CategoryCreationDto;
import com.springboot.minimartapi.product.dto.ProductCreationDto;
import com.springboot.minimartapi.product.dto.ProductEditionDto;
import com.springboot.minimartapi.product.ProductService;
import com.springboot.minimartapi.transaction.TransactionCreationDto;
import com.springboot.minimartapi.transaction.TransactionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
  private final ProductService productService;
  private final RoleService roleService;
  private final AdminService adminService;


  @PostMapping("/roles/create")
  void createRole(@Valid @RequestBody RoleCreationDto roleCreationDto){
    roleService.createRole(roleCreationDto);
  }

  @PatchMapping("/roles/edit/{roleId}")
  void editRole(@PathVariable Integer roleId, @RequestBody RoleEditionDto roleEditionDto){
     roleService.editRole(roleId, roleEditionDto);
  }

  @PatchMapping("/users/deactivate/{userId}")
  void deactivateUser(@PathVariable Long userId){
    adminService.deactivateUser(userId);
  }

  @DeleteMapping("/users/delete/{userId}")
  void deleteUser(@PathVariable Long userId){
    adminService.deleteUser(userId);
  }

  @PostMapping("/products/create")
  void  creatProduct(@Valid @RequestBody ProductCreationDto productCreationDto){
      productService.createProduct(productCreationDto);
  }
  @PatchMapping("/products/edit/{productId}")
  void  editProduct( @RequestBody ProductEditionDto productEditionDto, @PathVariable Long productId){
      productService.editProduct(productEditionDto, productId);
  }

  @DeleteMapping("/products/delete/{productId}")
  void deleteProduct(@PathVariable Long productId){
      productService.deleteProduct(productId);
    }

  @GetMapping("/products/checkqty/{productId}")
  Long checkStock(@PathVariable Long productId){
    return adminService.checkQtyOnHandOfProductId(productId);
  }
  @PostMapping("/categories/create")
   void  createCategory(@Valid @RequestBody CategoryCreationDto categoryCreationDto)  {
      productService.createCategory(categoryCreationDto);
  }
  @GetMapping("orders/toconfirm")
    List<AdminOrderDto> listOrderToConfirm(){
    return adminService.listOrderToConfirm();
  }

  @PostMapping("orders/confirm/{orderNumber}")
  Map<String, Object> confirmOrder(@PathVariable Long orderNumber){
    return adminService.confirmOrder(orderNumber);
  }

  @GetMapping("orders/todeliver")
  List<AdminOrderDto> listOrderToDeliver(){
    return adminService.listOrderToDeliver();
  }
  @GetMapping("orders/listitemtodeliver/{orderNumber}")
  List<OrderItemDto> listOderItem(@PathVariable Long orderNumber){
    return adminService.listOrderItemByOrderNumber(orderNumber);
  }

  @GetMapping("orders/address/{orderNumber}")
  String deliveryAddress(@PathVariable Long orderNumber ){
    return adminService.getDeliveryAddress(orderNumber);
  }

  @PostMapping("orders/deliver/{orderNumber}")
  Map<String, Object> deliver(@PathVariable Long orderNumber){
    return adminService.deliver(orderNumber);
  }
  @GetMapping("orders/delivering")
  List<AdminOrderDto> deliveringOrders(){
    return adminService.listDeliveringOrder();
  }

  @PostMapping("orders/complete/{orderNumber}")
  Map<String, Object> completeOrder(@PathVariable Long orderNumber){
    return  adminService.orderConfirmReceived(orderNumber);
  }

  @PostMapping("transactions/bookin")
  void bookProduct(@Valid @RequestBody TransactionCreationDto transactionCreationDto){
    adminService.bookProductInToStock(transactionCreationDto);
  }

  @GetMapping("/transactions")
  List<TransactionDto> transactionDtoList(){
    return adminService.transactionDtoList();
  }





}
