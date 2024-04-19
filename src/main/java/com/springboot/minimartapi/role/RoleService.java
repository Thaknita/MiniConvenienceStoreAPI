package com.springboot.minimartapi.role;

public interface RoleService {
    void createRole(RoleCreationDto roleCreationDto);

    void editRole(Integer roleId, RoleEditionDto roleEditionDto);


}
