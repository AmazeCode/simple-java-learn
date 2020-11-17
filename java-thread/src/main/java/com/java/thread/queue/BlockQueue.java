package com.java.thread.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 */
public class BlockQueue {

    /*
        如果存队列的时候，如果满了，就会等待，如果取队列的时候，如果获取不到也会等待。
     */
    public static void main(String[] args) throws InterruptedException {
        // 最多支持3个队列总数 阻塞式队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        blockingQueue.offer("张三");//添加非阻塞式队列
        boolean b1 = blockingQueue.offer("李四",3, TimeUnit.SECONDS);//添加阻塞式队列,设置时间为3秒
        blockingQueue.offer("小朱");
        //若没有消费的情况下，等待三秒“小张”是存入不到队列的，因为队列已经满了
        boolean b2 = blockingQueue.offer("小张",3, TimeUnit.SECONDS);
        System.out.println("b1:"+b1+"-----"+"b2:"+b2);
        //System.out.println(blockingQueue.poll());
        //不会等待 ，能取到值不会进行等待
        //System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
        //会进行等待，因为没有存第三个值，会触发等待，等待3秒取不到值就放弃
        //System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
    }
}
