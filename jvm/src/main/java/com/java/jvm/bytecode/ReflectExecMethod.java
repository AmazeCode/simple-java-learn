package com.java.jvm.bytecode;

import java.lang.reflect.Method;

/**
 * 使用反射执行方法
 */
public class ReflectExecMethod {

    public static void main(String[] args) throws Exception {
        //1、使用反射技术执行方法
        Class<?> forName = Class.forName("com.java.jvm.bytecode.ReflectExecMethod");
        Object newInstance = forName.newInstance();
        Method method = forName.getDeclaredMethod("sum",int.class,int.class);
        //执行方法,传入构造方法和参数
        method.invoke(newInstance,1,4);
    }

    public static void sum(int a , int b){
        System.out.println("sum:"+(a+b));
    }
}
