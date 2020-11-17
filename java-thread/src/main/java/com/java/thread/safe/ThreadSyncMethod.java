package com.java.thread.safe;

/**
 * 线程安全-同步方法保证线程的安全
 * 火车票售卖问题
 */
public class ThreadSyncMethod {

    public static void main(String[] args) {
        //创建线程
        ThreadSyncMethodDemo threadSyncMethodDemo = new ThreadSyncMethodDemo();
        Thread t1 = new Thread(threadSyncMethodDemo,"窗口1");
        Thread t2 = new Thread(threadSyncMethodDemo,"窗口2");
        //启动线程
        t1.start();
        t2.start();
    }
}

class ThreadSyncMethodDemo implements Runnable{

    //多个窗口共享100张票
    private int count = 100;

    @Override
    public void run() {
        while(count>0){
            sale();
        }
    }

    public synchronized void sale() {
        if(count>0){
            System.out.println(Thread.currentThread().getName()+":"+(100-count+1)+"张票");
            count--;
        }
    }
}
