package com.java.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  演示下lock锁(轻量级锁)是否具备可重入性 验证
 *  方式：set 、 get都有锁，如果set和get都能执行就说明具有可重入性
 *  特征:锁可以传递 (方法递归传递)，避免死锁现象
 */
public class LockReentrantValid extends Thread{

    // ReentrantLock 可重入锁
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        set();
    }

    //synchronized 是什么时候释放锁？
    public void set(){
        try{
            lock.lock();//锁定
            System.out.println("set方法");
            /*
                统一线程，外层函数获得锁之后，内层递归函数仍有获取该锁的权限---递归锁
                调用get时，锁会传递到get里面，因为get里面也有锁，这就是递归锁的特性
             */
            get();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();//释放锁
        }
    }

    public void get(){
        try{
            lock.lock();//锁定
            System.out.println("lock 可以具备可重入性-get方法");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockReentrantValid lockValid = new LockReentrantValid();
        lockValid.start();
    }
}
