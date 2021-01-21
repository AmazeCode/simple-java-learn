package com.java.spring.ioc.annotation.ext;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description: 自定义注入注解(功能相当于@Resource)
 * @author: zhangyadong
 * @date: 2020/12/26 16:27
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface ExtResource {
}
