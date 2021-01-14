package com.java.springboot.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //开启异步调用
public class SpringbootExceptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExceptionApplication.class, args);
    }

}
