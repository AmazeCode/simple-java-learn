package com.java.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池:可缓存线程池(用的比较多)
 */
public class NewCachedThreadPool {

    public static void main(String[] args) {
        //创建可缓存线程池 重复利用 无限大小
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        for(int i=0;i<10;i++){
            final int temp = i;
            // 可执行execute方法表示启动线程
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "," + temp);
                }
            });
        }
    }
}
