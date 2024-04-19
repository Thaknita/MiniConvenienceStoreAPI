package com.springboot.minimartapi.auth;


import com.springboot.minimartapi.auth.dto.AuthDto;
import com.springboot.minimartapi.auth.dto.LoginDto;
import com.springboot.minimartapi.auth.dto.UserRegistrationDto;
import com.springboot.minimartapi.auth.dto.VerifyUserDto;

import java.util.Map;

public interface AuthService {

    AuthDto login(LoginDto loginDto);
    Map<String, Object> registerUser (UserRegistrationDto userRegistrationDto) ;

    Map<String, Object> verifyUser (VerifyUserDto verifyUserDto);

}
