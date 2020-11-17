package com.java.thread.basic;

/**
 * 第二种创建线程的方式:实现Runnable接口(推荐：原因是java是单继承多实现单的机制，所以采用继承的方式更利于扩展)
 */
public class ThreadImplementRunnable {

    public static void main(String[] args) {
        System.out.println("main........主线程开始");

        //1、创线程
        ThreadDemoTwo threadDemoTwo = new ThreadDemoTwo();
        Thread thread = new Thread(threadDemoTwo);
        //2、启动线程
        thread.start();

        for (int i=0;i<10;i++){
            System.out.println("main.......i"+i);
        }
        System.out.println("main......主线程结束");
    }


}

//1、实现Runnable接口，重写run方法
class ThreadDemoTwo implements Runnable{

    //run 方法中需要执行代码
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            System.out.println("子.......i"+i);
        }
    }
}
