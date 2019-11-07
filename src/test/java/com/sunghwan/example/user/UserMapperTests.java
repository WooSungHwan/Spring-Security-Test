package com.sunghwan.example.user;

import com.sunghwan.example.ExampleApplication;
import com.sunghwan.example.user.domain.User;
import com.sunghwan.example.user.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
@WebAppConfiguration
public class UserMapperTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void readUserTest(){
        User user = userMapper.readUser("cusonar");
        assertThat("cusonar", is(user.getUsername()));

        user = userMapper.readUser("abc");
        assertThat("abc", is(user.getUsername()));

        //assertThat("YCU", is(user.getUsername())); 무조건 에러나는 코드
        //assertThat("1234", is(user.getPassword()));
    }

    @Test
    public void readAuthorityTest(){
        List<GrantedAuthority> authorities = userMapper.readAuthority("cusonar");

        for(GrantedAuthority auth : authorities){
            assertThat(authorities, hasItem(new SimpleGrantedAuthority(auth.getAuthority())));
        }
    }


}
