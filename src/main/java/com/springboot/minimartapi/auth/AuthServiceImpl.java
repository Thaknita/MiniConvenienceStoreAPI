package com.springboot.minimartapi.auth;

import com.springboot.minimartapi.role.RoleMapper;
import com.springboot.minimartapi.auth.dto.AuthDto;
import com.springboot.minimartapi.auth.dto.LoginDto;
import com.springboot.minimartapi.auth.dto.UserRegistrationDto;
import com.springboot.minimartapi.auth.dto.VerifyUserDto;
import com.springboot.minimartapi.user.User;

import com.springboot.minimartapi.user.UserRepo;
import com.springboot.minimartapi.user.UserService;
import com.springboot.minimartapi.util.RandomNumber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final UserRegistrationMapper userRegistrationMapper;
    private final AuthRepo authRepo;
    private final UserRepo userRepo;
    private final RoleMapper roleMapper;

    @Override
    public AuthDto login(LoginDto loginDto) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDto.emailAddress(),
                loginDto.password()
        );
        auth = daoAuthenticationProvider.authenticate(auth);
        log.info("Auth: {}", auth);
        log.info("Auth:{}", auth.getName());
        log.info("Auth: {}", auth.getAuthorities());
        return null;
    }
    @Transactional
    @Override
    public Map<String, Object> registerUser(UserRegistrationDto userRegistrationDto)  {
        if (!userRegistrationDto.password().equals(userRegistrationDto.confirmPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password doesn't match!");
        }
        String verifyDigit = RandomNumber.getSixDigit();
        userService.createUser(userRegistrationMapper.fromUserRegistrationDto(userRegistrationDto));
        authRepo.updateVerifiedCode(userRegistrationDto.emailAddress(), verifyDigit);
        return Map.of("Message","Please check your email to  verify your account ! ");
    }
    @Override
    public Map<String, Object> verifyUser(VerifyUserDto verifyUserDto) {

        User user = authRepo.findByEmailAddressAndVerifyCode(verifyUserDto.emailAddress(), verifyUserDto.verifyCode())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect verify code")
                );

        user.setIsVerified(true);
        user.setVerifyCode(null);

        authRepo.save(user);

        return Map.of("Message", "verified successfully");
    }
}
