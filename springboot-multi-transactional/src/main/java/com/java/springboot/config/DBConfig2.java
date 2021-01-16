package com.java.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: 数据库test02配置映射
 * @Author: zhangyadong
 * @Date: 2021/1/16 0016 下午 6:56
 * @Version: v1.0
 */
@Data
@ConfigurationProperties(prefix = "mysql.datasource.test02") //读取配置信息从mysql.datasource.test02开始
public class DBConfig2 {

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
