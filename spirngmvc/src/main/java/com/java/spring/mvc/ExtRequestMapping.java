package com.java.spring.mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 自定义RequestMapping注解
 * @Author: zhangyadong
 * @Date: 2020/12/27 20:22
 * @Version: v1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtRequestMapping {
}
