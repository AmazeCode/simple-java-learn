package com.java.spring.ioc.annotation;

import com.java.spring.ioc.annotation.ext.ExtClassPathAnnotationApplicationContext;
import com.java.spring.ioc.annotation.service.ExtCheckService;

/**
 * @Description: 自定义注解注入方式测试
 * @Author: zhangyadong
 * @Date: 2020/12/26 15:14
 * @Version: v1.0
 */
public class Test {

    /*
        使用注解版本事物的时候,第一步我们要做什么？ 扫包
        // 1.使用java反射机制扫包,获取包下所有的类
        // 2.判断类上是否存在注入bean的注解
        // 3.使用java反射机制进行初始化
     */
    public static void main(String[] args) throws Exception{
        ExtClassPathAnnotationApplicationContext app = new ExtClassPathAnnotationApplicationContext("com.java.spring.ioc.annotation.service.impl");
        ExtCheckService injectAnnotationService = (ExtCheckService)app.getBean("extCheckServiceImpl");
        System.out.println(injectAnnotationService);
        injectAnnotationService.add();
    }
}
