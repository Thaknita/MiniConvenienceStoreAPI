package com.springboot.minimartapi.security;


import com.springboot.minimartapi.user.User;
import com.springboot.minimartapi.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findUserByEmailAddressAndIsActiveAndIsVerified(username, true, true)
                .orElseThrow(() -> new UsernameNotFoundException("incorrect  password or email address!"));
        CustomUserDetail customUserDetail = new CustomUserDetail();
        customUserDetail.setUser(user);
        return customUserDetail;
    }
}
