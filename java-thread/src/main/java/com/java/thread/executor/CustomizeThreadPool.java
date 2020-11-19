package com.java.thread.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 */
public class CustomizeThreadPool {

    public static void main(String[] args) {

        /*
            参数说明:
            corePoolSize	核心线程数量，线程池维护线程的最少数量
            maximumPoolSize	线程池维护线程的最大数量
            keepAliveTime	线程池除核心线程外的其他线程的最长空闲时间，超过该时间的空闲线程会被销毁
            unit	        keepAliveTime的单位，TimeUnit中的几个静态属性：NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS
            workQueue	    线程池所使用的任务缓冲队列
            threadFactory	线程工厂，用于创建线程，一般用默认的即可
            handler	        线程池对拒绝任务的处理策略

            corePoolSize只能小于或者等于maximumPoolSize

            设计核心线程和最大线程数的目的是：保证所有真正的线程，都能被实际用到。核心线程数1---(实际运行的线程) 最大线程数2---最多可以创建多少个线程

            核心线程数1，最多只能创建2个线程，线程空闲超时时间0L,单位毫秒，keepAliveTime配置为0表示不回收线程
            线程空闲超时时间，一般配置30秒
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(3));

        /*
            情况一:如果当前线程池中的线程数目小于corePoolSize,则每来一任务,就会创建一个线程去执行这个任务;
         */
        //任务1 现在创建线程 在执行
        threadPoolExecutor.execute(new TaskThread("任务1"));

        /*
            情况二:如果当前线程池中的线程数目>=corePoolSize,则每来一个任务,会尝试将其添加到任务缓存队列中,若添加成功,则该任务会等待空闲线程将其取出执行;若添加失败（一般来说任务缓存队列已满）,
            则会创建新的线程去执行这个任务;
         */
        //任务2 存放在缓存队列，复用线程
        threadPoolExecutor.execute(new TaskThread("任务2"));
        //任务3 存放在缓存队列，复用线程
        threadPoolExecutor.execute(new TaskThread("任务3"));
        //任务4 存放在缓存队列，复用线程
        threadPoolExecutor.execute(new TaskThread("任务4"));
        /*
            情况三：如果队列已经满了,则总线程数不大于maximumPoolSize的前提下,则创建新的线程
         */
        //任务5 如果队列已经满了,则总线程数不大于maximumPoolSize的前提下,则创建新的线程
        threadPoolExecutor.execute(new TaskThread("任务5"));
        /*
            情况四：如果当前线程池中的数目达到maximumPoolSize,则会采取任务拒绝策略进行处理(抛出异常)
         */
        //任务6 会报错,超出了线程池的最大容纳大小(2(最大线程池数)+3(阻塞队列大小))
        //threadPoolExecutor.execute(new TaskThread("任务6"));
    }
}

class TaskThread implements Runnable{

    private String threadName;

    /**
     * 构造方法
     */
    public TaskThread(String threadName){
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+threadName);
    }
}
