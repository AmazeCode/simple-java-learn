package com.java.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池：可固定线程池(用的最多)
 */
public class NewFixedThreadPool {

    public static void main(String[] args) {
        /*
            newFixedThreadPool底层用的是BlockingQueue队列，超过3个的线程就放到阻塞队列中，一旦有人去消费的时候，才会出列，进行执行任务
            corePoolSize:核心线程数3个
            maximumPoolSize：最大线程数也是3个
         */
        // 可固定长度线程池  参数为最大线程数
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);

        for(int i=0;i<20;i++){
            final int temp = i;
            // 可执行execute方法表示启动线程
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "," + temp);
                }
            });
        }
    }
}
