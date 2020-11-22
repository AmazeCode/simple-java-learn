package com.java.thread.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 乐观锁：
 */
public class OptimisticLock implements Runnable{

    //共享全局便量
    //private int count = 1;
    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void run() {
        while(true){
            Integer count = getCount();
            if(count > 170){
                break;
            }
            System.out.println(count);
        }
    }

    public Integer getCount(){
        try{
            Thread.sleep(50);
        }catch (Exception e){

        }
        //每次自增 i++，线程安全，底层没有使用锁（CAS无锁机制）
        return atomicInteger.getAndIncrement();
    }

    public static void main(String[] args) {
        OptimisticLock optimisticLock = new OptimisticLock();
        Thread t1 = new Thread(optimisticLock);
        Thread t2 = new Thread(optimisticLock);
        t1.start();
        t2.start();
    }
}
