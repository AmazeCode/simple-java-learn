package com.java.thread.lock;

/**
 * 悲观锁：
 */
public class PessimisticLock implements Runnable{
    //共享全局便量
    private int count = 1;

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

    public synchronized Integer getCount(){
        try{
            Thread.sleep(50);
        }catch (Exception e){

        }
        return count ++;
    }

    //synchronized具有可重入性，保证原子性和可见性。缺点：影响效率，只能有一个锁进到线程中去
    public static void main(String[] args) {
        PessimisticLock pessimisticLock = new PessimisticLock();
        Thread t1 = new Thread(pessimisticLock);
        Thread t2 = new Thread(pessimisticLock);
        t1.start();
        t2.start();
    }
}
