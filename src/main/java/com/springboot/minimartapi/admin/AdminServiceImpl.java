package com.springboot.minimartapi.admin;
import com.springboot.minimartapi.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepo userRepo;
    @Transactional
    @Override
    public void deactivateUser(Long userId) {
        userRepo.deactivateUser(userId);
    }
    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteByUserId(userId);
    }
}
