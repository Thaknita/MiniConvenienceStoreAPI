package com.springboot.minimartapi.admin;

import com.springboot.minimartapi.order.dto.AwaitToConfirmDto;
import com.springboot.minimartapi.role.RoleCreationDto;
import com.springboot.minimartapi.role.RoleEditionDto;
import com.springboot.minimartapi.role.RoleService;
import com.springboot.minimartapi.product.category.CategoryCreationDto;
import com.springboot.minimartapi.product.dto.ProductCreationDto;
import com.springboot.minimartapi.product.dto.ProductEditionDto;
import com.springboot.minimartapi.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/categories/create")
    void  createCategory(@Valid @RequestBody CategoryCreationDto categoryCreationDto)  {
      productService.createCategory(categoryCreationDto);
    }

    @GetMapping("orders/toconfirm")
    List<AwaitToConfirmDto> listOrderToConfirm(){
    return adminService.listOrderToConfirm();
    }
















}
