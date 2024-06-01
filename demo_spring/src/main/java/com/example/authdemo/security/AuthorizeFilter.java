package com.example.authdemo.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authdemo.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class AuthorizeFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    private final AntPathRequestMatcher mather = new AntPathRequestMatcher("/api/public/**");

    public AuthorizeFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * フィルター処理
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!mather.matches(request)) {
            String xAuthToken = request.getHeader("X-AUTH-TOKEN");
            if (xAuthToken == null || !xAuthToken.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            // トークンの検証
            String token = xAuthToken.substring(7);
            DecodedJWT decodedJWT = this.tokenService.decode(token);

            // ログイン状態の設定
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(decodedJWT.getClaim("username").toString(), null, new ArrayList<>()));
        }
        filterChain.doFilter(request, response);
    }
}
