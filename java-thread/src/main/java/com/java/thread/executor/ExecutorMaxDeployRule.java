package com.java.thread.executor;

/**
 *  线程池配置原则
 */
public class ExecutorMaxDeployRule {

    /*
        阻塞的情况产生的原因:请求、读数据库、循环可能发生阻塞
        http.post()----阻塞2秒
        IO密集：该任务需要大量的IO阻塞、如果是在单线程中执行阻塞，解决阻塞情况下，可以使用多线程技术。
        (配置线程数(最大线程)=2*CPU核数(cpu逻辑处理器个数),win通过任务管理器看)
     */

    /*
        CPU运算能力,不会浪费等待资源
        CPU密集：执行该任务的时候不会产生IO阻塞,CPU运行的时候速度特别块。(配置线程数(最大线程数)=cpu核数)
     */

    /*
        win 任务管理器查看cpu核数(cpu逻辑处理器个数)
        Linux：
        查看物理CPU个数：cat /proc/cpuinfo| grep "physical id"| sort| uniq| wc -l
        查看每个物理CPU中core的个数(即核数)：cat /proc/cpuinfo| grep "cpu cores"| uniq
        查看逻辑CPU的个数（线程池配置需要参考值）：cat /proc/cpuinfo| grep "processor"| wc -l
     */
}
