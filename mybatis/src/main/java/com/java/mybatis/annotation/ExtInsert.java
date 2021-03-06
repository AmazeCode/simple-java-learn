package com.java.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @Description: 自定义插入注解
 * @Author: zhangyadong
 * @Date: 2021/1/2 16:55
 * @Version: v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtInsert {

    String value();
}
