
package com.springboot.minimartapi.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;
    @Override
    public void createRole(RoleCreationDto roleCreationDto) {
      Role role =  roleMapper.fromRoleCreationDto(roleCreationDto);
      roleRepo.save(role);
    }
    @Transactional
    @Override
    public void editRole(Integer roleId, RoleEditionDto roleEditionDto) {
        String roleName = roleEditionDto.roleName();
        roleRepo.updateRoleName(roleId, roleName );

    }


}
