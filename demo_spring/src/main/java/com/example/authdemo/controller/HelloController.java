package com.example.authdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/private/hello")
    public String privateHello() {
        return "Hello User";
    }

    @GetMapping("/api/public/hello")
    public String publicHello() {
        return "Hello Anonymous";
    }
}
