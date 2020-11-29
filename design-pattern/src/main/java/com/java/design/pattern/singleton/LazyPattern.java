package com.java.design.pattern.singleton;

/**
 * @Description: 懒汉式 模式
 * @author: zhangyadong
 * @Date: 2020/11/25 11:27
 * @version: v1.0
 */
public class LazyPattern {

    /*
        类初始化的时候,不会创建该对象，真正需要的时候才会去加载(创建)，天生线程不安全，需要去自己解决线程安全问题(synchronized),效率比较低
     */
    private static LazyPattern lazyPattern;

    //私有化构造函数
    private LazyPattern(){}

    /*
     * 版本一：懒汉式(线程不安全)模式,(不推荐使用)
     */
    public static LazyPattern getInstance() {
        if (lazyPattern == null) {
            lazyPattern = new LazyPattern();
        }
        return lazyPattern;
    }

    /*
        版本二:懒汉式(线程安全)模式
        存在问题：多个线程访问LazyPattern的时候，可能会创建多个对象，会违背单例原则
        static和synchronized同时使用,每次只能保证一个线程进行执行效率比较低   (不推荐使用)
     */
    public static synchronized LazyPattern getInstanceTS(){
        if(lazyPattern == null){
            lazyPattern = new LazyPattern();
        }
        return lazyPattern;
    }

    public static void main(String[] args) {
        LazyPattern l1 = LazyPattern.getInstance();
        LazyPattern l2 = LazyPattern.getInstance();
        System.out.println(l1 == l2);
    }
}
