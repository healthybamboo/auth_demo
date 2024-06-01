package com.example.authdemo.service;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenService {
    String generate(String username);

    DecodedJWT decode(String token);
}
