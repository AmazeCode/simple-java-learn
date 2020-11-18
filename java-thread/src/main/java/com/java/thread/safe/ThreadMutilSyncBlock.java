package com.java.thread.safe;

/**
 * 线程安全-同步块(多线程)
 */
public class ThreadMutilSyncBlock {

    public static void main(String[] args) {

        //创建线程
        ThreadMutilSyncBlockDemo threadMutilSyncBlockDemo1 = new ThreadMutilSyncBlockDemo();
        ThreadMutilSyncBlockDemo threadMutilSyncBlockDemo2 = new ThreadMutilSyncBlockDemo();
        Thread t1 = new Thread(threadMutilSyncBlockDemo1,"窗口1");
        Thread t2 = new Thread(threadMutilSyncBlockDemo2,"窗口2");
        System.out.println("");
        //启动线程
        t1.start();
        t2.start();
    }
}

class ThreadMutilSyncBlockDemo implements Runnable{

    /*
       创建两个线程的情况,代码块的参数需要静态化,共享的全局参数也需要静态化
    */
    //同时多个窗口共享100
    private static int count = 100;

    /*
      定义锁,static 类型的全局变量是存放在方法区的,供所有线程共享(java内存结构)
     */
    private static Object obj = new Object();

    @Override
    public void run() {
        while(count>0){
            sale();
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
    public void sale(){
        synchronized (obj){//自定义锁
            if(count>0){
                System.out.println(Thread.currentThread().getName()+",出售"+(100-count+1)+"张票");
                count--;
            }
        }
    }
}
