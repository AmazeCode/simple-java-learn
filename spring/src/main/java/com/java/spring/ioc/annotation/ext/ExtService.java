package com.java.spring.ioc.annotation.ext;

import java.lang.annotation.*;

/**
 * 自定义Service注解 注入bean容器(功能相当于@Service)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtService {
}
