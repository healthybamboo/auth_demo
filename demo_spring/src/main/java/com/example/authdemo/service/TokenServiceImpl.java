package com.example.authdemo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authdemo.config.TokenConfiguration;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    private static final Long EXPIRATION_TIME = 1000L * 60L * 60L * 1L;

    private final Algorithm algorithm;

    private final TokenConfiguration tokenConfiguration;

    public TokenServiceImpl(TokenConfiguration tokenConfiguration) {
        this.tokenConfiguration = tokenConfiguration;
        this.algorithm = Algorithm.HMAC256(this.tokenConfiguration.getSecret());
    }

    @Override
    public String generate(String username) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        // ヘッダ部へのアルゴリズムとハッシュ値をしていする
        Algorithm algorithm = Algorithm.HMAC256(this.tokenConfiguration.getSecret());

        // トークンを生成する
        String token = JWT.create()
                .withIssuer(tokenConfiguration.getTokenIssuer())  // トークンの発行者
                .withSubject(username) // トークンの対象
                .withIssuedAt(issuedAt) // トークンの発行日時
                .withExpiresAt(expiresAt) // トークンの有効期限
                .sign(this.algorithm); // 署名

        return token;
    }

    @Override
    public DecodedJWT decode(String token) {
        JWTVerifier verifier = JWT.require(this.algorithm)
                .withIssuer(tokenConfiguration.getTokenIssuer())
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }
}
