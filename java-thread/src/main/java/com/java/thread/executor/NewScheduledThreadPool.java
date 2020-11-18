package com.java.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池:定时线程池
 */
public class NewScheduledThreadPool {

    public static void main(String[] args) {
        // 参数为最大线程数
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(3);

        for(int i=0;i<20;i++){
            final int temp = i;
            // schedule启动定时，设置为3秒后执行
            newScheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "," + temp);
                }
            },3, TimeUnit.SECONDS);
        }
    }
}
