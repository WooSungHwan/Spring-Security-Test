package com.sunghwan.example.user.service;

import com.sunghwan.example.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {

    //권한가져오기
    List<GrantedAuthority> getAuthorities(String username);
    //유저 조회
    public User readUser(String username);
    //유저 등록
    public void createUser(User user);
    //유저 삭제
    public void deleteUser(String username);
    //암호화
    public PasswordEncoder passwordEncoder();

}