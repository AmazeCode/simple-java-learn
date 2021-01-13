package com.java.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @description: 第三种启动方式
 * @author: zhangyadong
 * @date: 2021/1/13 15:12
 */
@SpringBootApplication
public class Springboot01Application {

    /*
        @SpringBootApplication 被 @Configuration @EnableAutoConfiguration @ComponentScan 注解所修饰
        扫包范围:在启动类上加上@SpringBootApplication注解,当前包下所有的类以及子包下的所有类都可以扫到
        @SpringBootApplication 等同于 @EnableAutoConfiguration+@ComponentScan("com.java.springboot.controller")
     */
    public static void main(String[] args) {
        SpringApplication.run(Springboot01Application.class, args);
    }
}
