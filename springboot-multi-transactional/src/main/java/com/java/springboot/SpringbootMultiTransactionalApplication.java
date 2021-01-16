package com.java.springboot;

import com.java.springboot.config.DBConfig1;
import com.java.springboot.config.DBConfig2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
// 开启读取配置文件
@EnableConfigurationProperties(value = { DBConfig1.class, DBConfig2.class})
@SpringBootApplication
public class SpringbootMultiTransactionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMultiTransactionalApplication.class, args);
    }

}
