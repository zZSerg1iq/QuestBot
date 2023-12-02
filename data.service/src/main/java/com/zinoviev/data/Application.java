package com.zinoviev.data;

import com.zinoviev.data.repository.UserRepository;
import com.zinoviev.data.service.UserRepositoryService;
import com.zinoviev.entity.data.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EntityScan("com.zinoviev.entity")
public class Application {

    private static UserRepositoryService userRepositoryService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    static void addUser(){

    }
}
