package com.springboot.minimartapi.user;

import com.springboot.minimartapi.role.Role;
import com.springboot.minimartapi.security.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void createUser(UserCreationDto userCreationDto) {

        User users = userMapper.fromUserCreationDto(userCreationDto);

        if (userRepo.existsByUserName(users.getUserName())){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Username is taken");
        }
        if (userRepo.existsByEmailAddress(users.getEmailAddress())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Address is conflict");
        }

        users.setIsActive(true);
        users.setIsVerified(false);
        users.setPassword( passwordEncoder.encode(userCreationDto.password()) );

        userRepo.save(users);

    }
}
