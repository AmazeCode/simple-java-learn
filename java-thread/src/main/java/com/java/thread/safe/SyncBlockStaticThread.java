package com.java.thread.safe;

/**
 * 静态同步方法
 */
public class SyncBlockStaticThread {

    /*
           1、线程安全问题
           什么是线程安全问题: d当多个线程共享一个全局变量,注意写操作可能受到其他线程的干扰，
           读操作是不会发生线程安全。---java内存模型
       */
    public static void main(String[] args) throws InterruptedException{

        //1、创建线程
        SyncBlockStaticThreadDemo syncBlockStaticThreadDemo = new SyncBlockStaticThreadDemo();
        Thread t1 = new Thread(syncBlockStaticThreadDemo,"窗口1");
        Thread t2 = new Thread(syncBlockStaticThreadDemo,"窗口2");
        //启动线程
        t1.start();
        Thread.sleep(40);
        syncBlockStaticThreadDemo.flag = false;
        t2.start();
    }
}

/*
    非静态同步方法使用的时什么锁呢？
    得出结论:
    1、非静态同步方法使用的时this锁
    2、静态同步方法使用的是当前class字节码文件
 */
class SyncBlockStaticThreadDemo implements Runnable{
    //同时多个窗口共享100
    private static int count = 100;
    private static Object obj = new Object();
    public boolean flag = true;

    @Override
    public void run() {
        if(flag){
            while(count>0){
                //参数使用obj不能同步,原因是obj是自定义的并不是锁
                synchronized(this){
                    try{
                        Thread.sleep(20);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(count>0){
                        System.out.println(Thread.currentThread().getName()+",出售"+(100-count+1)+"张票");
                        count--;
                    }
                }
            }
        }else{
            while(count>0){
                sale();
            }
        }
    }

    /*
        synchronized
        解决线程安全的方式:
        内置锁(synchronized),保证线程的原子性，当线程进入方法的时候，自动获取锁，一旦锁被其他线程获取后，其他的线程就会等待。
        锁的特征：只能有一个线程进行使用。
        怎么去释放锁，程序执行完毕之后，就会把锁释放。 注意：加锁之后会提高程序的安全性，但是会降低程序的运行效率(降低效率原因：程序运行之前会进行获取锁的资源，
        会去进行竞争,如果锁被其他线程占用，程序则会进行等待，变为休眠状态)--锁的资源的竞争
        synchronized是重入锁：可以被重复传递使用的，最终能够保证整个的互斥
        内置锁也叫做互斥锁：即A线程拿到锁的时候，其他线程不能获取锁，A线程把锁释放后，其他线程才能拿到锁
        使用方式：同步方法、同步代码块

        显示锁(Lock)
     */
    public static synchronized void sale(){//加上static的同步方法锁使用的是当前的字节码文件
        synchronized(SyncBlockStaticThreadDemo.class){//参数接收任意的全局变量
            try{
                Thread.sleep(20);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(count>0){
                System.out.println(Thread.currentThread().getName()+",出售"+(100-count+1)+"张票");
                count--;
            }
        }
    }
}
