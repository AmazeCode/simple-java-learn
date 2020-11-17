package com.java.thread.basic;

/**
 * 多线程Join方法使用
 */
public class ThreadJoin {

    public static void main(String[] args) {
        try{
            System.out.println("main........主线程开始");

            //1、创建线程
            JoinDemo joinDemo = new JoinDemo();
            Thread thread = new Thread(joinDemo);
            //启动线程
            thread.start();
            thread.join();//让子线程执行完后主线程再执行
            for(int i=0;i<10;i++){
                System.out.println("main......i"+i);
            }
            System.out.println("main.......主线程结束");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

//1、实现runnable接口,重写run方法
class JoinDemo implements Runnable{

    //run 方法中需要执行代码
    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println("子.......i"+i);
        }
    }
}