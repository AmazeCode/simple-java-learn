package com.ac.commonmistakes.lock;

/**
 * @Description: 锁定对象分类
 * @Author: zhangyadong
 * @Date: 2021/5/7 8:51
 * @Version: v1.0
 */
public class SynchronizedScopeDemo {

    /*
        锁使用的三种场景以及锁定的对象说明
        1、Synchronized修饰静态方法的时候（对应testMethod1），锁的是当前类的class对象，对应到这里就是LockTest.class_对象_。
        2、Synchronized修饰实例方法的时候（对应testMethod2），锁的是当前类实例的对象，对应到这里就是LocKTest中的this引用_对象_。
        3、Synchronized修饰同步代码块的时候（对应testMethod3），锁的是同步代码块括号里的对象实例，对应到这里就是obj_对象_。
     */

    Object obj=new Object();

    public static synchronized void testMethod1() {
        //同步代码。
    }

    public synchronized void testMethod2() {
        //同步代码
    }

    public void testMethod3() {

        synchronized (obj) {
            //同步代码
        }
    }
}
