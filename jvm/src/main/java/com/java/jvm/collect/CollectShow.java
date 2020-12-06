package com.java.jvm.collect;

/**
 * @Description: 垃圾回收机制演示
 * @Author: zhangyadong
 * @Date: 2020/12/6 0006 下午 4:18
 * @Version: v1.0
 */
public class CollectShow {

    /*
        什么是不可达对象：没有被继续引用 没有存活 没有被继续使用
     */
    public static void main(String[] args) {
        CollectShow collectShow = new CollectShow();
        collectShow = null;//不可达 提示jvm回收
        System.gc();//提示给gc进行回收垃圾 误区：提示给jvm垃圾回收机制进行回收，但是不表示立即进行回收。
        //gc线程是守护线程，依赖主线程存在
    }

    //finalize 属于objcet类
    @Override
    protected void finalize() throws Throwable {
        //垃圾回收之前 会进行执行的方法
        System.out.println("垃圾回收机制要开始执行我的方法啦！！.........");
    }
}
