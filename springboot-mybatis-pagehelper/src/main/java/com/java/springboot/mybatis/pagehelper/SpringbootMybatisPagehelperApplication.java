package com.java.springboot.mybatis.pagehelper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.java.springboot.mybatis.pagehelper.mapper")
public class SpringbootMybatisPagehelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisPagehelperApplication.class, args);
    }

}
