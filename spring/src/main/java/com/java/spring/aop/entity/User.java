package com.java.spring.aop.entity;


import com.java.spring.aop.annotation.ExtAdd;

import java.lang.reflect.Method;

/**
 * 测试注解
 */
public class User {

    @ExtAdd(userName = "张三",userId = 18,arrays={"1"})
    public void add(){

    }

    /**
     * @description: 测试是否能够读取到手写的ExtAdd注解
     * @params: [args]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/21 15:45
     */
    public static void main(String[] args) throws Exception {
        //怎么样获取到方法上的注解信息  反射机制
        Class<?> forName = Class.forName("com.java.spring.aop.entity.User");
        //获取当前类 (不包含继承)
        Method[] methods = forName.getDeclaredMethods();
        for (Method method : methods){
            System.out.println("方法名称："+method.getName());
            //获取该方法上是否存在注解
            ExtAdd extAdd = method.getDeclaredAnnotation(ExtAdd.class);
            if(extAdd == null){
                System.out.println("该方法上没有加注解.....");
                continue;
            }
            //在该方法上查找到该注解
            System.out.println(extAdd.userId());
            System.out.println(extAdd.userName());
            System.out.println(extAdd.arrays());

            //目的是换行
            System.out.println();
        }
    }
}
