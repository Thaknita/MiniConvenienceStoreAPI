package com.springboot.minimartapi.admin.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    @Modifying
    @Query("""
    UPDATE Role as r
    SET r.roleName = ?2 where r.id=?1
    """)
    void updateRoleName(Integer roleId, String roleName);


}
