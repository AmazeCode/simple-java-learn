package com.java.thread.basic;

/*
    第三种创建线程的方式:内部类的形式
 */
public class ThreadInnerClass {

    public static void main(String[] args) {
        System.out.println("main.......主线程开始");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println("子.......i"+i);
                }
            }
        });
        //设置守护线程,跟随主线程一块消亡
        //t.setDeamon(true);
        t.start();
        for(int i=0;i<10;i++){
            System.out.println("main......i"+i);
        }
        System.out.println("main.......主线程结束");
    }
}
