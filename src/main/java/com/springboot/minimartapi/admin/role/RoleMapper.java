
package com.springboot.minimartapi.admin.role;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role fromRoleCreationDto (RoleCreationDto roleCreationDto );
    Role fromRoleRegistrationDto (RoleRegistrationDto roleRegistrationDto);

    Role fromRoleDto(RoleDto roleDto);




}
