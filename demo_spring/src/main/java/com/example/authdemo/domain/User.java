package com.example.authdemo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    /**
     * ユーザー名
     */
    private String username;


    /**
     * パスワード
     */
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
