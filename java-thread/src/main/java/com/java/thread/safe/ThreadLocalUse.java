package com.java.thread.safe;

/**
 * ThreadLocal使用
 * ThreadLocal:为线程提供共享局部变量,解决线程安全
 * ThreadLocal底层是map
 */
public class ThreadLocalUse extends Thread{

    private Res res;

    public ThreadLocalUse(Res res){
        this.res = res;
    }

    @Override
    public void run() {
        for(int i=0;i<3;i++){
            System.out.println(Thread.currentThread().getName()+","+res.getNumber());
        }
    }

    public static void main(String[] args) {
        Res res = new Res();
        ThreadLocalUse t1 = new ThreadLocalUse(res);
        ThreadLocalUse t2 = new ThreadLocalUse(res);
        t1.start();
        t2.start();
    }
}

class Res{

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public int getNumber(){
        //从threadLocal中获取值
        int count = threadLocal.get()+1;
        //往threadLocal中存值
        threadLocal.set(count);
        return count;
    }
}