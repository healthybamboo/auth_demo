package com.example.authdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestBody {
    private String username;
    private String password;
}
