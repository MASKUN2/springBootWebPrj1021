package com.maskun.springboot.config.auth;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //인증논리를 추가할 위치
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        if("john".equals(username) && "12345".equals(password)){
            return new UsernamePasswordAuthenticationToken(username,password, Arrays.asList());
        }else {
            throw new AuthenticationCredentialsNotFoundException("인증오류");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Authentication 형식의 구현을 추가할 위치
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
