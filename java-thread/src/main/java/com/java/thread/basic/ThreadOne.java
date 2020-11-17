package com.java.thread.basic;

/*
    1、什么是线程，线程是一条执行路径，每个线程互不影响
    2、什么是多线程，多线程在一个进程中，有多条不同执行路径，并行执行。目的为了提高执行效率。
    3、在一个进程中一定会有哪个线程？--  一个进程中一定会有主线程
    4、如果连主线程都没有，怎么执行程序
    //线程的几种分类 用户线程、守护线程
    //主线程、子线程、gc线程
    //1、继承Thread类
    //2、实现Runnable接口
    //3、使用匿名内部类方式
    //4、使用线程池进行管理（实际工作中使用）
    多线程包含异步概念
 */
public class ThreadOne {

    public static void main(String[] args) {
        System.out.println("main..........主线程开始");
        //1、创建线程
        ThreadDemoOne threadDemoOne = new ThreadDemoOne();
        //2、启动线程
        threadDemoOne.start();
        for (int i=0;i<10;i++){
            System.out.println("main........i"+i);
        }
        System.out.println("main...........主线程结束");
    }
}

/*
    第一种创建多线程的方式:继承thread类
 */
class ThreadDemoOne extends Thread{

    @Override
    public void run() {
        for(int i=0; i<10;i++){
            System.out.println("子.....i"+i);
        }
    }
}
