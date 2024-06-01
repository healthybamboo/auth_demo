package com.example.authdemo.controller;

import com.example.authdemo.dto.LoginRequestBody;
import com.example.authdemo.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class AuthController {
    private final TokenService tokenService;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    public AuthController(TokenService tokenService, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.tokenService = tokenService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestBody requestBody
    ) {
        try {
            // DaoAuthenticationProvideを用いた認証を行う
            this.daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(requestBody.getUsername(), requestBody.getPassword()));

            // トークンを生成
            String token = this.tokenService.generate(requestBody.getUsername());

            // クライアントへ返却
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("X-AUTH-TOKEN", "Bearer " + token);
            System.out.println("username:" + requestBody.getUsername());
            System.out.println("password:" + requestBody.getPassword());
            System.out.println("token:" + token);
            return new ResponseEntity(httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
