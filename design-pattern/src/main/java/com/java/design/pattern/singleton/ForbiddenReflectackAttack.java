package com.java.design.pattern.singleton;

import java.lang.reflect.Constructor;

/**
 * @Description: 饿汉式(禁止反射攻击模式)
 * @Author: zhangyadong
 * @Date: 2020/11/28 21:39
 * @Version: v1.0
 */
public class ForbiddenReflectackAttack {

    //类的初始化的时候，就会创建对象，天生线程安全，调用效率比较高，如果在不使用对象的时候，会浪费内存
    private static final ForbiddenReflectackAttack forbiddenReflectackAttack = new ForbiddenReflectackAttack();
    //定义一个静态标志位，用于防止对象的重复创建
    private static boolean FLAG = false;

    //1、将构造函数私有化
    private ForbiddenReflectackAttack() {
        //一定要使用synchronized，保证线程安全
        synchronized(ForbiddenReflectackAttack.class){
            System.out.println("FLAG:"+FLAG);
            if(FLAG == false){
                //这段代码的意思是将代码修改为true
                FLAG = !FLAG;
            }else{
                System.out.println("该对象是单例的，不能够重复创建!");
                throw new RuntimeException("该对象是单例的，不能够重复创建!");
            }
            System.out.println("ForbiddenReflectackAttack对象" + FLAG);
        }
    }

    //会有线程安全问题吗？为什么不会产生线程安全问题?
    public static ForbiddenReflectackAttack getInstance(){
        System.out.println("getInstance()");
        return forbiddenReflectackAttack;
    }

    public static void main(String[] args) throws Exception{
        ForbiddenReflectackAttack forbiddenReflectackAttack = new ForbiddenReflectackAttack();
        //使用反射初始化对象
        Class<?> forName = Class.forName("com.java.design.pattern.singleton.ForbiddenReflectackAttack");
        //调用参构造函数
        Constructor<?> constructor = forName.getDeclaredConstructor(null);
        //允许访问私有构造函数
        constructor.setAccessible(true);
        //初始化对象
        Object o = constructor.newInstance();
    }
}
