package com.java.thread.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 非阻塞队列
 */
public class NoBlockQueue {

    /*
        阻塞队列最大的好处，能够防止队列容器溢出，防止丢失数据
     */
    public static void main(String[] args) {

        //非阻塞队列
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<String>();
        concurrentLinkedQueue.offer("小张");
        concurrentLinkedQueue.offer("小李");
        concurrentLinkedQueue.offer("小朱");
        //获取队列 只能获取一个元素 poll():获取后会删除队列  peek():获取队列值不会删除队列
        System.out.println(concurrentLinkedQueue.poll());
        System.out.println(concurrentLinkedQueue.poll());
        System.out.println(concurrentLinkedQueue.poll());
        System.out.println(concurrentLinkedQueue.poll());
        System.out.println(concurrentLinkedQueue.size());
    }
}
