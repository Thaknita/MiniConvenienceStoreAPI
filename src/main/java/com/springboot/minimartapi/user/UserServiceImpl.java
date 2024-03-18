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
    private final PasswordEncryptor passwordEncryptor;
    @Override
    public void createUser(UserCreationDto userCreationDto) {

        User users = userMapper.fromUserCreationDto(userCreationDto);

        if (userRepo.existsByUserName(userCreationDto.userName())){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Username is taken");
        }
        if (userRepo.existsByEmailAddress(userCreationDto.emailAddress())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Address is conflict");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                        .roleName("USER")
                .build());

        users.setIsActive(true);
        users.setIsVerified(false);
        users.setRoles(roles);

        userRepo.save(users);



    }
}
