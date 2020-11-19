package com.java.thread.executor;

/**
 * 线程池配置原则：Cpu密集型例子（即:什么叫做Cpu密集）
 */
public class CpuDense extends Thread{

    @Override
    public void run() {

        //省略特别多代码、省略。。。。。,没有阻塞
        //阻塞的情况产生的原因:请求、读数据库、循环
        //http.post()----阻塞2秒
        //IO密集：该任务需要大量的IO操作、产生阻塞、如果是在单线程中执行阻塞，可以使用多线程技术。(多配置线程数=2*CPU核数(cpu处理器个数),win通过任务管理器看)
        //CPU运算能力,不会浪费等待资源
        //CPU密集：执行该任务的时候不会产生IO阻塞,CPU运行的时候特别块。
    }
}
