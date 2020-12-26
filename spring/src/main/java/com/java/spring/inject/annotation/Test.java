package com.java.spring.inject.annotation;

import com.java.spring.inject.annotation.ext.ExtClassPathAnnotationApplicationContext;
import com.java.spring.inject.annotation.service.InjectAnnotationService;

/**
 * @Description: 测试
 * @Author: zhangyadong
 * @Date: 2020/12/26 15:14
 * @Version: v1.0
 */
public class Test {

    public static void main(String[] args) throws Exception{
        ExtClassPathAnnotationApplicationContext app = new ExtClassPathAnnotationApplicationContext("com.java.spring.inject.annotation.service.impl");
        InjectAnnotationService injectAnnotationService = (InjectAnnotationService)app.getBean("injectAnnotationServiceImpl");
        System.out.println(injectAnnotationService);
        injectAnnotationService.add();
    }
}
