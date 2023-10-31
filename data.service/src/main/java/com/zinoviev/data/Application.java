package com.zinoviev.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.zinoviev.entity")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
