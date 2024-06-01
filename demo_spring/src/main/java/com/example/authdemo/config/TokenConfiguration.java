package com.example.authdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {
    @Value("${token.secret}")
    private String secret;

    @Value("${token.issuer}")
    private String tokenIssuer;

    public String getSecret() {
        return this.secret;
    }

    public String getTokenIssuer() {
        return this.tokenIssuer;
    }
}
