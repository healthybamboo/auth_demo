package com.example.authdemo.repository;

import com.example.authdemo.domain.User;

public interface UserRepository {
    User findByName(String name);
}
