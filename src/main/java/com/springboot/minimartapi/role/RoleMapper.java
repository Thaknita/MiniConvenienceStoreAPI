
package com.springboot.minimartapi.role;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role fromRoleCreationDto (RoleCreationDto roleCreationDto );

}
