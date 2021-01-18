package com.java.springboot.hot.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class SpringbootHotDeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHotDeployApplication.class, args);
    }
}
