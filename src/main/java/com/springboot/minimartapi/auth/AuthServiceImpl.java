package com.springboot.minimartapi.auth;

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

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final UserRegistrationMapper userRegistrationMapper;
    private final AuthRepo authRepo;

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

        authRepo.updateVerifiedCode(userRegistrationDto.emailAddress(), verifyDigit);

        userService.createUser(userRegistrationMapper.fromUserRegistrationDto(userRegistrationDto));


        return Map.of("Message","Please check your email to  verify your account ! ");
    }
}
