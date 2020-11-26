package com.java.design.pattern.found.singleton;

/**
 * @Description: 饿汉式
 * @author: zhangyadong
 * @Date: 2020/11/25 11:13
 * @version: v1.0
 */
public class HungryPattern {

    //类初始化的时候，就会创建对象，天生线程安全，调用效率比较高，如果在不适用对象的时候，会浪费内存
    //声明为静态的,存放在jvm的方法区(永久区),垃圾回收机制并不会区回收永久区对象
    private static final HungryPattern hungryPattern = new HungryPattern();

    //1、私有化构造函数，避免使用new创建对象
    private HungryPattern(){

    }

    /*
        问题：多线程情况下会有线程安全问题吗？
        多个线程去读同一个全局变量的时候,不会产生线程安全问题,因为加了static final不能被改变
        (不推荐使用)
     */
    public static HungryPattern getInstance(){
        return hungryPattern;
    }

    /*
        验证:两个对象是否一致，一致说明是同一个对象
     */
    public static void main(String[] args) {
        HungryPattern h1 = HungryPattern.getInstance();
        HungryPattern h2 = HungryPattern.getInstance();
        System.out.println(h1 == h2);
    }
}
