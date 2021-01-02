package com.java.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @Description: 自定义参数注解
 * @Author: zhangyadong
 * @Date: 2021/1/2 17:00
 * @Version: v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ExtParam {

    String value();
}
