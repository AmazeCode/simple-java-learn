package com.java.spring.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * @Target(value = ElementType.METHOD) 设置注解权限(ElementType.TYPE:类上使用；ElementType.METHOD:方法上使用)</br>
 * @Retention(RetentionPolicy.RUNTIME) 设置注解生命周期(RetentionPolicy.RUNTIME:项目启动生效)</br>
 */
@Target(value = ElementType.METHOD)//定义注解适用范围:只能在方法上使用
@Retention(RetentionPolicy.RUNTIME)//设置注解生命周期
public @interface ExtAdd {

    int userId() default 0;

    String userName() default "默认名称";

    String[] arrays();
}
