package com.java.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 数据库test01配置映射
 * @Author: zhangyadong
 * @Date: 2021/1/16 0016 下午 6:56
 * @Version: v1.0
 */
@Data
@ConfigurationProperties(prefix = "mysql.datasource.test01") //读取配置信息从mysql.datasource.test01开始
public class DBConfig1 {

    private String url;

    private String username;

    private String password;

    private int minPoolSize;

    private int maxPoolSize;

    private int maxLifetime;

    private int borrowConnectionTimeout;

    private int loginTimeout;

    private int maintenanceInterval;

    private int maxIdleTime;

    private String testQuery;
}
