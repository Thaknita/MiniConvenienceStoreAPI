package com.springboot.minimartapi.auth;

import com.springboot.minimartapi.auth.dto.UserRegistrationDto;
import com.springboot.minimartapi.user.UserCreationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    UserCreationDto fromUserRegistrationDto(UserRegistrationDto userRegistrationDto);

}
