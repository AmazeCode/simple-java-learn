package com.java.springboot.hot.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class SpringbootHotDeployApplication {

    /*
        SpringBoot性能调优
        1、JVM参数调优：
            影响到整体运行,吞吐量
            调优策略：初始化堆内存与最大值相同，减少垃圾回收次数
        2、扫包优化:
            是在启动优化@SpringBootApplication等同于@ComponentScan(basePackages = {"com.java.springboot"})+@Configuration,
            扫包的核心是扫同级包递归遍历包下子类，如果项目特别大的时候,会扫到无用包，会影响到项目启动时间
            解决方案：项目特别大时,使用@ComponentScan(basePackages = {"com.java.springboot"})+@Configuration替代@SpringBootApplication
        3、默认Tomcat容器修改为Undertow
        4、tomcat参数调优
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootHotDeployApplication.class, args);
    }
}
