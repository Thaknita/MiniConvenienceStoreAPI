package com.springboot.minimartapi.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    AuthDto login(@Valid @RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }
    @PostMapping("/register")
    Map<String, Object> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto){
      return  authService.registerUser(userRegistrationDto);
    }

}
