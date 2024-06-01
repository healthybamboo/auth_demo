package com.example.authdemo.repository;

import com.example.authdemo.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User findByName(String name) {
        // user : user
        // password: password
        return new User("user", "$2a$10$DbY.c8.ZxWo3/igrUv1ae.V2JVLc1xWMcBt2672PREflm7bQRfdOO");
    }
}
