package com.springboot.minimartapi.auth;


import java.util.Map;

public interface AuthService {

    AuthDto login(LoginDto loginDto);
    Map<String, Object> registerUser (UserRegistrationDto userRegistrationDto) ;

    Map<String, Object> verifyUser (VerifyUserDto verifyUserDto);

}
