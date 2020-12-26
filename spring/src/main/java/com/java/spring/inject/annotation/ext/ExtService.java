package com.java.spring.inject.annotation.ext;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义注解 Service 注入bean容器
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtService {
}
