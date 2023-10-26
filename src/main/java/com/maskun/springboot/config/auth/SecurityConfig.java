package com.maskun.springboot.config.auth;
import com.maskun.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 설정을 활성화해줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.csrf().disable().headers().frameOptions().disable()//브라우저에서 H2 콘솔을 사용하기 위해서 보안옵션을 끄는 것으로 나중에 다시 키면 되겠다.
            .and()
                .authorizeRequests().antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**")
                    .permitAll()//매처에 해당하는 URL요청은 공개상태로 두는 것
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()//매처에 해당하는 URL요청과 그외(.anyRequest())은 권한필요상태로 두는 것
            .and()
                .logout().logoutSuccessUrl("/")//로그아웃 성공시 index페이지로 보냄
            .and()
                .oauth2Login() //소셜로그인(구글) 성공 후 진행할 UserService구현체를 등록한다. 리소스서버(소셜서비스에서 있는 회원정보를제공)에서 가져온 정보로 어떻게 진행할 것인지를 지정한다.
                .userInfoEndpoint()
                .userService(customOAuth2UserService);


    }
}
