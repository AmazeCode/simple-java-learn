package com.java.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.java.springboot.mapper"}) // 多个包使用逗号隔开
public class SpringbootMybatisApplication {

    /*
        @MapperScan(basePackages = {"com.java.springboot.mapper"})//这样配置mapper层可以不用使用@Mapper注解
        怎么能扫描到的原理：通过反射技术读取到包下所有的类,将类放到容器中
        1.mybatis启动方式可以在mapper层不需要加@Mapper注解,但是一定要在启动类的时候加上@MapperScan通过包路径去定位
        2.mybatis在接口上加@Mapper注解,就不需要在启动类上加@MapperScan(basePackages = {"com.java.springboot.mapper"})
        3.实际使用的时候推荐使用@MapperScan(basePackages = {"com.java.springboot.mapper"})方式,能减少添加@Mapper的次数,减少错误
     */

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
    }

}
