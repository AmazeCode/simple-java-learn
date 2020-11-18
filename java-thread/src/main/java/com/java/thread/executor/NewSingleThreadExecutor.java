package com.java.thread.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池：单线程化的线程池
 */
public class NewSingleThreadExecutor {

    public static void main(String[] args) {
        //创建一个单线程化的线程池
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

        for(int i=0;i<10;i++){
            final int temp = i;
            // 可执行execute方法表示启动线程
            newSingleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "," + temp);
                }
            });
        }
        //停掉线程池
        newSingleThreadExecutor.shutdown();
    }
}
