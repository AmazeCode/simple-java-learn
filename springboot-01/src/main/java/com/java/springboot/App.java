package com.java.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description: 这是第二种启动方式加上扫包
 * @Author: zhangyadong
 * @Date: 2021/1/13 14:25
 * @Version: v1.0
 */
@ComponentScan("com.java.springboot.controller")
@EnableAutoConfiguration
public class App {

    /*
        SpringBoot项目中如何整合静态资源？
        SpringBoot默认提供静态资源位置需置于classpath下,目录名需符合如下规则:
        "classpath:/META-INF/resources/",
        "classpath:/resources/",
        "classpath:/static/",
        "classpath:/public/"
        "/"：当前项目的根路径
        我们可以在resources目录下创建static,在该位置放置一个图片文件dog.png。启动应用程序后,尝试访问http://localhost:8080/dog.png
        如能显示成功，配置成功
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
