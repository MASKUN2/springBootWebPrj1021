package com.maskun.springboot.config.auth;

import com.maskun.springboot.domain.user.User;
import com.maskun.springboot.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("인증 실패");
        User u = userRepository.findUserByUsername(username)
                .orElseThrow(s);
        return new CustomUserDetails(u);
    }
}
