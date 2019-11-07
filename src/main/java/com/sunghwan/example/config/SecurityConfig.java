package com.sunghwan.example.config;

import com.sunghwan.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

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
                // 사용자의 쿠키에 세션을 저장하지 않겠다는 옵션. Rest 아키텍쳐는 Stateless를 조건으로 하기 때문에 세션정책을 STATELESS로.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                //요청에 대해서 권한 처리를 하겠다.
                .authorizeRequests()
                    //로그인 로직 커스터마이징
                    .antMatchers("/user/login").permitAll()
                    /*
                        크롬과 같은 브라우저에서는 실제 GET, POST 요청을 하기 전에 OPTIONS를 preflight 요청합니다.
                        이는 실제 서버가 살아있는지를 사전에 확인하는 요청입니다.
                        Spring에서는 OPTIONS에 대한 요청을 막고 있으므로
                        해당 코드를 통해서 OPTIONS 요청이 왔을 때도 오류를 리턴하지 않도록 해줍니다.
                     */
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/user").hasAuthority("USER")
                    .antMatchers("/admin").hasAuthority("ADMIN")
                    //어떠한 요청에라도 인증을 요구하겠다.
                    .anyRequest().permitAll()
                    .and()

                .formLogin().and() //Form을 이용한 로그인을 사용하겠다. -> 안쓰면 커스터마이징
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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy(){
        return new HeaderHttpSessionStrategy();
    }

}
