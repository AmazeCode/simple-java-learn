package com.java.springboot.write.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * springmvc 配置信息
 * # @EnableWebMvc:开启springmvc功能
 * # @Configuration 标记类是配置文件类
 * # @ComponentScan 开始扫包
 * @Author: zhangyadong
 * @Date: 2021/1/20 0020 下午 10:10
 * @Version: v1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.java.springboot.write.controller"}) //扫描controller包
public class WebConfig extends WebMvcConfigurerAdapter {

    // 需要配置视图转换器
    // 创建SpringMVC视图解析器
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        // 可以在JSP页面中通过${}访问beans
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;
    }

}
