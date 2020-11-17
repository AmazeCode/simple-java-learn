package com.java.thread.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 队列方式实现生产者和消费者
 */
public class ProduceConsumeQueue {

    public static void main(String[] args) {
        /*
            可以传递容量也可以不传递容量，默认会有个容量
            LinkedBlockingQueue:内部会有一个默认的容量，当超过这个容量的时候会进行扩容的，因此它是一个伪的无界队列
         */
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(10);
        //创建线程
        ProducerThread producerThread = new ProducerThread(blockingQueue);
        ConsumerThead consumerThead = new ConsumerThead(blockingQueue);
        Thread p1 = new Thread(producerThread);
        Thread c1 = new Thread(consumerThead);
        //启动生产者
        p1.start();
        //启动消费者
        c1.start();
        try {
            //等待10秒时间
            Thread.sleep(1000 * 10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //停止生产者生产
        producerThread.stop();
    }
}

/*
    生产者队列 添加队列
 */
class ProducerThread implements Runnable{

    //定义阻塞队列
    private BlockingQueue<String> blockingQueue;
    //添加volatile保证可见性以及拒绝重排序
    private volatile boolean FLAG = true;
    //原子类
    AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 构造方法
     */
    public ProducerThread(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        System.out.println("######生产者程序已启动######");
        try{
            while(FLAG){
                String data = atomicInteger.incrementAndGet()+ "";
                //像队列中存值
                boolean offer = blockingQueue.offer(data,2, TimeUnit.SECONDS);
                if(offer){
                    System.out.println("生产者存入队列成功！"+ data);
                }else{
                    System.out.println("生产者存入队列失败！"+ data);
                }
                //让程序等待1秒，便于观察结果
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            System.out.println("生产者程序已经结束");
        }
    }

    /*
        供其他调用
     */
    public void stop(){
        this.FLAG = false;
    }
}

class ConsumerThead implements Runnable{

    //定义阻塞队列
    private BlockingQueue<String> blockingQueue;
    //添加volatile保证可见性以及拒绝重排序，这里可以不用加，因为此时只是在本地内存中操作的
    private volatile boolean FLAG = true;
    //原子类
    AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 构造方法
     */
    public ConsumerThead(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        System.out.println("消费者线程已经启动............");
        try{
            while(FLAG){
                String data = blockingQueue.poll(2,TimeUnit.SECONDS);
                /*
                    data为""时是不会不会走进if里面的，所以不需要加为空字符串判断
                 */
                if(data == null){
                    System.out.println("超过2秒钟时间，没有获取到队列信息");
                    FLAG = false;//跳出循环
                    return;
                }
                System.out.println("消费者获取到data:"+data);
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            System.out.println("消费者线程已经结束............");
        }
    }
}