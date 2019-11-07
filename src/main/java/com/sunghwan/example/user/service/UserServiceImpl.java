package com.sunghwan.example.user.service;

import com.sunghwan.example.user.domain.User;
import com.sunghwan.example.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    public List<GrantedAuthority> getAuthorities(String username) {
        List<GrantedAuthority> authorities = userMapper.readAuthority(username);
        return authorities;
    }

    @Override
    public User readUser(String username) {
        return userMapper.readUser(username);
    }

    @Override
    public void createUser(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        userMapper.createUser(user);
        userMapper.createAuthority(user);
    }

    @Override
    public void deleteUser(String username) {
        userMapper.deleteUser(username);
        userMapper.deleteAuthority(username);
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.readUser(username);
        user.setAuthorities(getAuthorities(username));

        return user;
    }
}
