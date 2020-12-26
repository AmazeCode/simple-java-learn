package com.java.spring.annotation;

import java.lang.reflect.Method;

/**
 * 测试注解
 */
public class User {

    @AddAnnotation(userName = "张三",userId = 18,arrays={"1"})
    public void add(){

    }

    public void del(){

    }

    public static void main(String[] args) throws Exception {
        //怎么样获取到方法上的注解信息  反射机制
        Class<?> forName = Class.forName("com.java.spring.annotation.User");
        //获取当前类 (不包含继承)
        Method[] methods = forName.getDeclaredMethods();
        for (Method method : methods){
            System.out.println("方法名称："+method.getName());
            //获取该方法上是否存在注解
            AddAnnotation addAnnotation = method.getDeclaredAnnotation(AddAnnotation.class);
            if(addAnnotation == null){
                System.out.println("该方法上没有加注解.....");
                continue;
            }
            //在该方法上查找到该注解
            System.out.println(addAnnotation.userId());
            System.out.println(addAnnotation.userName());
            System.out.println(addAnnotation.arrays());

            //目的是换行
            System.out.println();
        }

    }
}
