package com.java.thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

/**
 * 读写锁 jvm内置缓存
 * 两个线程：读读可以共存，读写不能共存，写写也不能共存
 */
public class  ReadWriteLock {

    //volatile只能保证可见性，并不能保证原子性(即线程安全)
    private volatile Map<String,String> cache = new HashMap<>();

    //读写锁
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    //写入锁
    private WriteLock writeLock = rwl.writeLock();
    //读取锁
    private ReadLock readLock = rwl.readLock();

    //写入元素
    public void put(String key,String value){
        try{
            writeLock.lock();
            System.out.println("写入put方法key:"+key+",value:"+value+"开始");
            Thread.currentThread().sleep(100);
            cache.put(key,value);
            System.out.println("写入put方法key:"+key+",value:"+value+"结束");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

    //读取元素
    public String get(String key){
        try{
            readLock.lock();
            System.out.println("读取key:"+key+", 开始");
            Thread.currentThread().sleep(100);
            String value = cache.get(key);
            System.out.println("读取key:"+key+", 结束");
            return value;
        }catch (Exception e){
           return null;
        }finally{
            readLock.unlock();
        }
    }

    public static void main(String[] args) {

        ReadWriteLock readWriteLock = new ReadWriteLock();

        //写线程
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++){
                    readWriteLock.put(i+"",i+"");
                }
            }
        });

        //读线程
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    readWriteLock.get(i+"");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
