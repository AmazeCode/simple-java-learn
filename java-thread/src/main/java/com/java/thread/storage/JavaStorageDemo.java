package com.java.thread.storage;

/**
 * java内存模型的例子
 * volatile作用：对另一个线程可见，及时将修改的变量刷新到主内存，
 * volatile与synchronized区别：
 * 1、可见性方面：volatile只能保证可见，但是不能保证原子性。
 * 2、性能方面：volatile性能比synchronized高，但是volatile不能替代synchronized，因为volatile不能保证原子性
 * 项目中，什么时候使用volatile：只要是全局共享变量，全部都加上volatile
 *
 */
public class JavaStorageDemo {

    public static void main(String[] args) throws  InterruptedException{
        StorageThread storageThread = new StorageThread();
        storageThread.start();
        Thread.sleep(3000);
        storageThread.setRunning(false);//主线程改变值
        System.out.println("flag已经改为false");
        Thread.sleep(1000);
        System.out.println("flag:"+storageThread.flag);
    }
}

class StorageThread extends Thread{

    /*
        不加volatile前后对照，不加volatile之前3秒后子线程还是在执行，加了之后三秒后子线程会停止,会输出“线程结束”字样
        （原因:main主线程把flag值该为false之后，子线程并不知道，子线程读取的还是本地内存中的值，所以子线程还在执行，加过volatile关键字之后主线程改变
        flag值之后，会立刻刷新到子线程中）
     */
    public volatile boolean flag = true;

    @Override
    public void run() {
        System.out.println("线程开始..........");
        while(flag){

        }
        System.out.println("线程结束..........");
    }

    public void setRunning(boolean flag){
        this.flag = flag;
    }
}