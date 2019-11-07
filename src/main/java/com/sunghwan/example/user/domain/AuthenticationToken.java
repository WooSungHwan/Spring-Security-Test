package com.sunghwan.example.user.domain;

import lombok.Data;

import java.util.Collection;

@Data
public class AuthenticationToken {
    private String username;
    private Collection authorities;
    private String token;

    public AuthenticationToken (String username, Collection collection, String token) {
        this.username = username;
        this.authorities = collection;
        this.token = token;
    }
}
