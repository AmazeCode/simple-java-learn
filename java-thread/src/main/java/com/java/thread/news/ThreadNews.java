package com.java.thread.news;

/**
 * 多线程间通讯：生产者与消费者
 */
public class ThreadNews {

    public static void main(String[] args) {
        Res res = new Res();
        ProduceThread produceThread = new ProduceThread(res);
        ConsumerThread consumerThread = new ConsumerThread(res);
        produceThread.start();
        consumerThread.start();
    }
}

/**
 * 生产者
 */
class ProduceThread extends Thread{

    public Res res;

    public ProduceThread(Res res){
        this.res = res;
    }

    @Override
    public void run() {

        int count = 0;

        while(true){
            synchronized (res){
                try{
                    if(res.flag == true){
                        res.wait();//让线程等待,释放当前锁对象
                    }
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(count == 0){
                    res.name = "小红";
                    res.sex = "女";
                }else{
                    res.name = "小军";
                    res.sex = "男";
                }
                //修改count值
                count = (count+1)%2;//0 1 0 1
                res.flag = true;
                res.notify();//唤醒被等待的线程
            }
        }
    }
}

/**
 *  消费者
 */
class ConsumerThread extends Thread{

    public Res res;

    public ConsumerThread(Res res){
        this.res = res;
    }

    /*
        原则上读是不需要添加synchronized为什么这里需要添加，原因是生产者和消费者是并行通讯执行的，并且都是在做死循环操作
        ，写的时候会进行读，如果读不加同步会造成读取的值有误差
     */
    @Override
    public void run() {
        while(true){
            synchronized (res){
                try{
                    if(res.flag == false){
                        res.wait();//线程进入等待,自动释放锁
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(res.name+","+res.sex);
                res.flag = false;
                res.notify();
            }
        }

    }
}

/*
 共享对象
 */
class Res{
    //姓名
    public String name;
    //年龄
    public String sex;
    /*
        为true的情况下：允许读不允许写
        为false的情况下：允许写不允许读
     */
    public boolean flag = false;
}