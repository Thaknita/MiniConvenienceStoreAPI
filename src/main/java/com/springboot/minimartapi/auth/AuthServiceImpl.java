package com.springboot.minimartapi.auth;

import com.springboot.minimartapi.user.UserRepo;
import com.springboot.minimartapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserService userService;
    private final UserRepo userRepo;
    private final UserRegistrationMapper userRegistrationMapper;

    @Override
    public AuthDto login(LoginDto loginDto) {
        return null;
    }

    @Override
    public Map<String, Object> registerUser(UserRegistrationDto userRegistrationDto)  {

        if (!userRegistrationDto.password().equals(userRegistrationDto.confirmPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password doesn't match!");

        }

        userService.createUser(userRegistrationMapper.fromUserRegistrationDto(userRegistrationDto));

        return Map.of("Message","Please check your email to  verify your account ! ");
    }
}
