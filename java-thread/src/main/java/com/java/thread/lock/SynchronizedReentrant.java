package com.java.thread.lock;

/**
 * synchronized具备可重入性判断
 * 重入锁 轻量级（Lock）与重量级锁（synchronized）---可重入性（递归锁）
 *
 * 验证：synchronized是可重复锁
 * 方式: 同步set方法调用get方法，如果get方法和set方法都能正常执行，说明synchronized为可重入锁
 */
public class SynchronizedReentrant implements Runnable{

    @Override
    public void run() {
        set();
    }

    /*
        synchronized什么时候释放锁?
        方法执行完自动释放锁
     */
    public synchronized void set(){
        System.out.println("set方法");
        get();
    }

    public synchronized void get(){
        System.out.println("synchronized 具备可重复性  get方法");
    }

    public static void main(String[] args) {
        SynchronizedReentrant synchronizedReentrant = new SynchronizedReentrant();
        Thread t = new Thread(synchronizedReentrant);
        t.start();
    }
}
