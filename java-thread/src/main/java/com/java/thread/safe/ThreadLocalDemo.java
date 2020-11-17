package com.java.thread.safe;

/**
 * ThreadLocal例子
 * 什么是ThreadLocal:给每个线程提供局部变量,解决线程安全
 * ThreadLocal底层是map
 */
public class ThreadLocalDemo extends Thread{

    private Res res;

    public ThreadLocalDemo(Res res){
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
        ThreadLocalDemo t1 = new ThreadLocalDemo(res);
        ThreadLocalDemo t2 = new ThreadLocalDemo(res);

        t1.start();
        t2.start();
    }
}

class Res{
    /*
        ThreadLocal,避免数据共享
     */
    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){

        protected Integer initialValue(){
            return 0;
        };
    };
    public Integer getNumber(){
        //从threadLocal中获取值
        int count = threadLocal.get()+1;
        //往threadLocal中存值
        threadLocal.set(count);
        return count;
    }
}
