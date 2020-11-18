package com.java.thread.safe;

/**
 * 区分：同步代码块、静态同步方法、非静态同步方法的锁是什么锁
 *
 * 结论:
 * 同步代码块、静态同步方法、非静态同步方法的锁分别是：
 * 同步代码块可以使用自定义的Object对象，也可以使用this或者当前类的字节码文件(类名.class)；
 * 静态同步方法的锁是当前类的字节码文件(类名.class)；
 * 非静态同步方法的锁是this；
 *
 * 证明方法:
 * 两个线程之间实现同步，一个线程使用同步代码块，一个线程使用同步方法。
 * 如果这两个线程同步了，说明了使用的是同一个锁;(也即是不会出现重复售卖的情况)
 */
public class ThreadLockTypeVerify {

    public static void main(String[] args) {

        //创建线程
        ThreadLockTypeVerifyDemo threadLockTypeVerifyDemo = new ThreadLockTypeVerifyDemo();
        Thread t1 = new Thread(threadLockTypeVerifyDemo,"窗口1");
        Thread t2 = new Thread(threadLockTypeVerifyDemo,"窗口2");

        //启动线程
        t1.start();
        t2.start();
    }
}

/**
 * 售票线程类
 */
class ThreadLockTypeVerifyDemo implements Runnable{

    //共享车票总数
    private static Integer count = 100;

    //标记位
    private static boolean flag = true;

    private static Object obj = new Object();//自定义锁

    @Override
    public void run() {
        while(count>0){
            if(flag){//执行同步代码块
                saleBlockForSynchronizedMethod();//验证同步方法
                //验证静态同步方法
                //saleBlockForStaticSynchronizedMethod();
            }else{
                synchronizedMethod();//同步方法
                //同步方法
                //staticSynchronizedMethod();
            }
            flag = !flag;
        }
    }

    /**
     * 同步代码块,用来验证同步方法持有的锁
     */
    public void saleBlockForSynchronizedMethod(){
        //同步代码块，包裹需要解决线程安全问题的代码块，两个线程同时访问(锁为this锁)
        synchronized (this){//同一时间只有一个线程能够访问
            if(count>0){//不加判断，会出现超售的情况
                System.out.println(Thread.currentThread().getName()+",出售第"+(100-count+1)+"张票");
                count--;
            }
        }
    }

    /**
     * 同步代码块,用来验证静态同步方法持有的锁
     */
    public void saleBlockForStaticSynchronizedMethod() {
        // 同步代码块，包裹需要解决线程安全问题的代码块，两个线程同时访问(锁为当前类的字节码文件)
        synchronized (ThreadLockTypeVerifyDemo.class) { // 只能有一个线程进行访问，必须拿到锁的时候才能访问
            if (count > 0) { // 不加判断，会出现超售的情况
                System.out.println(Thread.currentThread().getName() + ", 出售第" + (100 - count + 1) + "张票");
                count--;
            }
        }
    }

    /**
     * 同步方法
     */
    public synchronized void synchronizedMethod() {
        if (count > 0) { // 不加判断，会出现超售的情况
            System.out.println(Thread.currentThread().getName() + ", 出售第" + (100 - count + 1) + "张票");
            count--;
        }
    }

    /**
     * 静态同步方法
     */
    public static synchronized void staticSynchronizedMethod() {
        if (count > 0) { // 不加判断，会出现超售的情况
            System.out.println(Thread.currentThread().getName() + ", 出售第" + (100 - count + 1) + "张票");
            count--;
        }
    }
}
