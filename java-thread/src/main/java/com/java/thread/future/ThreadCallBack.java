package com.java.thread.future;

import java.util.concurrent.*;

/**
 * CallBack
 */
public class ThreadCallBack {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        Future<String> submit = newCachedThreadPool.submit(new ThreadCallBackDemo());
        System.out.println("1、主程序开始执行.........");
        /*
            调用get方法之前的程序不会等待，get方法之后的程序都会等待，为了不让主程序等待，把get方法放在新的子线程中
            不影响主线程去执行其他操作
         */
        //获取执行结果(新创建个子线程去接收执行结果，不去影响主线程)
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String result = submit.get();
                    System.out.println("2、result：" + result);
                    //Future模式
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("主线程正常执行任务！");
    }
}

class ThreadCallBackDemo implements Callable<String>{
    @Override
    public String call() throws Exception {
        System.out.println("3、正在执行任务，需要等待五秒钟时间，执行任务开始");
        Thread.sleep(5000);
        System.out.println("4、正在执行任务，需要等待五秒钟时间，执行任务结束");
        return "小张";
    }
}
