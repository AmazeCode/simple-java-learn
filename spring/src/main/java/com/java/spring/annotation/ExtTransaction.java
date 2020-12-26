package com.java.spring.annotation;

import java.lang.annotation.*;

/**
 * 自定义事物注解
 * 设置传播行为
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtTransaction {
}
