package com.sunghwan.example.user;

import com.sunghwan.example.ExampleApplication;
import com.sunghwan.example.user.domain.User;
import com.sunghwan.example.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
@WebAppConfiguration
public class UserServiceTests {

    private User user1;

    @Autowired
    private UserService userService;

    @Before
    public void setup(){
        user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("admin");
        user1.setAccountNonExpired(true);
        user1.setAccountNonLocked(true);
        user1.setName("ADMIN");
        user1.setCredentialsNonExpired(true);
        user1.setEnabled(true);
        user1.setAuthorities(AuthorityUtils.createAuthorityList("USER","ADMIN"));
    }

    @Test
    public void createUserTest(){
        userService.deleteUser(user1.getUsername());

        userService.createUser(user1);
        User user = userService.readUser(user1.getUsername());
        assertThat(user.getUsername(), is(user1.getUsername()));

        PasswordEncoder passwordEncoder = userService.passwordEncoder();
        assertThat(passwordEncoder.matches("admin", user.getPassword()), is(true));

        List<GrantedAuthority> authorities = user1.getAuthorities();
        for(GrantedAuthority auth : authorities){
            assertThat(authorities, hasItem(new SimpleGrantedAuthority(auth.getAuthority())));
        }
    }
}
