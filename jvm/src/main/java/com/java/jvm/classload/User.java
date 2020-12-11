package com.java.jvm.classload;

/**
 * 用户类：测试类加载机制
 * 静态代码块和静态属性的加载是顺序执行的,谁在下面最后显示的值是谁的
 */
public class User {

    private static int num = 10;

    static{
        num = 20;
    }

    public void show(){
        System.out.println(num);
    }
}
