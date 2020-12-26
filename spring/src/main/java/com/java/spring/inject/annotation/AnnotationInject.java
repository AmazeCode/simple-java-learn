package com.java.spring.inject.annotation;

/**
 * @Description: 手动实现注解注入
 * @Author: zhangyadong
 * @Date: 2020/12/26 12:12
 * @Version: v1.0
 */
public class AnnotationInject {

    //使用注解版本事物的时候,第一步我们要做什么？ 扫包
    public static void main(String[] args) {
        // 1.使用java反射机制扫包,获取包下所有的类
        // 2.判断类上是否存在注入bean的注解
        // 3.使用java反射机制进行初始化
    }
}
