package com.springboot.minimartapi.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminService {

    void deactivateUser(Long userId);
    void deleteUser(Long userId);

}
