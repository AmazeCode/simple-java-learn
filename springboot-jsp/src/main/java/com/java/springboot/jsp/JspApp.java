package com.java.springboot.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Description: 启动类
 * @Author: zhangyadong
 * @Date: 2021/1/13 0013 下午 10:00
 * @Version: v1.0
 */
@SpringBootApplication
public class JspApp extends SpringBootServletInitializer {
    /*
        注意：SpringBoot整合jsp,使用IDEA启动项目时,访问会找不到jsp页面,推荐使用maven方式启动,或者使用外置tomcat启动方式
     */
    public static void main(String[] args) {
        SpringApplication.run(JspApp.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JspApp.class);
    }
}
