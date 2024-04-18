package com.springboot.minimartapi.admin.role;

public interface RoleService {
    void createRole(RoleCreationDto roleCreationDto);

    void editRole(Integer roleId, RoleEditionDto roleEditionDto);


}
