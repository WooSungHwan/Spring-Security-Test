package com.sunghwan.example.config;

import com.sunghwan.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //. csrf().disable() : 이건 csrf(Cross Site Request Forgery)를 기본적으로 요청하는데, 이것을 하기 위해서는 따로 처리하는게 필요하므로 일단은 disable 처리합니다.
                .csrf().disable()
                //요청에 대해서 권한 처리를 하겠다.
                .authorizeRequests()
                    .antMatchers("/user").hasAuthority("USER")
                    .antMatchers("/admin").hasAuthority("ADMIN")
                    //어떠한 요청에라도 인증을 요구하겠다.
                    .anyRequest().authenticated()
                    .and()
                .formLogin() //Form을 이용한 로그인을 사용하겠다.
                    .and()
                .logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                //커스터마이징한 UserService를 적용하기 위해.
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

}
