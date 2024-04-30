package com.springboot.minimartapi.auth;


import com.springboot.minimartapi.auth.dto.*;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface AuthService {
    AuthDto refresh(RefreshTokenDto refreshTokenDto);

    AuthDto login(LoginDto loginDto);
    Map<String, Object> registerUser (UserRegistrationDto userRegistrationDto) throws MessagingException;

    Map<String, Object> verifyUser (VerifyUserDto verifyUserDto);

}
