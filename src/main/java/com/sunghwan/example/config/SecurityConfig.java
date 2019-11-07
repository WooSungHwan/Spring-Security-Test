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
                    // 추가메소드
                    /*
                        successHandler
                        : Login 성공시 추가 작업을 정의합니다.
                        솔루션의 추가 인증이 필요한 경우 사용할 수 있습니다.
                        SimpleUrlAuthenticationSuccessHandler 클래스를 extends 하거나
                        AuthenticationSuccessHandler를 구현해서
                        @Component로 정의한 후 autowired 해서 넣어주시면 됩니다.

                        ailureHandler
                        : Login 실패시 추가 작업을 정의할 수 있습니다.
                        패스워드 몇회 이상 오류 시 계정 잠금 기능을 사용할 수 있습니다.
                        SimpleUrlAuthenticationFailureHandler 클래스를 extends 하거나
                        AuthenticationFailureHandler를 구현해서 사용하시면 됩니다.

                     */

                    .and()
                .logout();
                    //추가 메소드
                    /*
                        logoutSuccessHandler : Logout 성공시 추가 작업을 정의합니다.
                        1.a에서 솔루션 인증을 추가한 경우, 솔루션 Logout 처리를 해주면 좋을것 같습니다.
                        마찬가지로 SimpleUrlLogoutSuccessHandler 클래스를 extends 하거나
                        LogoutSuccessHandler를 구현해서 사용하시면 됩니다.
                    */
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                //커스터마이징한 UserService를 적용하기 위해.
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

}
