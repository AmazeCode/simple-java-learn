package com.java.springboot.write.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 根配置
 *
 * @Author: zhangyadong
 * @Date: 2021/1/20 0020 下午 10:17
 * @Version: v1.0
 */
@Configuration
@ComponentScan("com.java.springboot.write") //扫描整个项目
public class RootConfig {
}
