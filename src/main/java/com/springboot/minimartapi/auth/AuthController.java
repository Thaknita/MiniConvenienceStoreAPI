package com.springboot.minimartapi.auth;

import com.springboot.minimartapi.auth.dto.AuthDto;
import com.springboot.minimartapi.auth.dto.LoginDto;
import com.springboot.minimartapi.auth.dto.UserRegistrationDto;
import com.springboot.minimartapi.auth.dto.VerifyUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    Map<String, Object> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto){
      return  authService.registerUser(userRegistrationDto);
    }

    @PostMapping("/verify")
    Map<String , Object>  verify(@Valid @RequestBody VerifyUserDto verifyUserDto){
        return authService.verifyUser(verifyUserDto);
    }

}
