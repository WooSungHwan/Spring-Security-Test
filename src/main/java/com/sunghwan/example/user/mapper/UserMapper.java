package com.sunghwan.example.user.mapper;

import com.sunghwan.example.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Mapper
public interface UserMapper {

    public User readUser(@Param("username") String username);

    public List<GrantedAuthority> readAuthority(@Param("username") String username);

    public void createUser(User user);

    public void createAuthority(User user);

    public void deleteUser(String username);

    public void deleteAuthority(String username);
}
