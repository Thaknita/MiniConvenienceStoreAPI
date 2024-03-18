package com.springboot.minimartapi.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromUserCreationDto (UserCreationDto userCreationDto);

}
