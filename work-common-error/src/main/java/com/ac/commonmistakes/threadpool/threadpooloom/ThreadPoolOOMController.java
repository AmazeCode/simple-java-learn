package com.ac.commonmistakes.threadpool.threadpooloom;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description: 线程池溢出 【线程池线程管理策略详解、线程池的声明需要手动进行】
 * @Author: zhangyadong
 * @Date: 2021/5/7 17:45
 * @Version: v1.0
 */
@Slf4j
@RequestMapping("threadpooloom")
@RestController
public class ThreadPoolOOMController {

    /*
        执行程序后不久，日志中就出现了如下 OOM：Exception in thread "http-nio-45678-ClientPoller" java.lang.OutOfMemoryError: G
        原因:
        newFixedThreadPool 方法的源码不难发现，线程池的工作队列直接 new 了一个LinkedBlockingQueue，而默认构造方法的 LinkedBlockingQueue 是一个
        Integer.MAX_VALUE 长度的队列，可以认为是无界;
        虽然使用 newFixedThreadPool 可以把工作线程控制在固定的数量上，但任务队列是无界
        的。如果任务较多并且执行较慢的话，队列可能会快速积压，撑爆内存导致 OOM。
     */
    @GetMapping("fixedOOML")
    public void fixedOOML() throws InterruptedException {
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);

        // 打印线程池的信息
        printStats(threadPool);

        for (int i = 0; i< 100000000; i++) {
            threadPool.execute(() -> {
                String payload = IntStream.rangeClosed(1, 1000000).mapToObj(__ -> "a").collect(Collectors.joining("")) + UUID.randomUUID().toString();

                try {
                    // 休眠一个小时,模拟程序执行耗时一个小时
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                }
            });
        }

        // 线程等待
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    /*
        同样会出现oom
        原因:
        翻看 newCachedThreadPool 的
        源码可以看到,这种线程池的最大线程数是 Integer.MAX_VALUE，可以认为是没有上限的,而其工作队列 SynchronousQueue 是一个没有存储空间的阻塞队列。这意味着,
        只要有请求到来,就必须找到一条工作线程来处理,如果当前没有空闲的线程就再创建一条新的。由于我们的任务需要1个小时才能执行完成,大量的任务进来后会创建大量线程。
        我们知道线程是需要分配一定的内存空间作为线程栈的,比如1M 因此无限制创建线程必然会导致oom
     */
    @GetMapping("cachedOOML")
    public void cachedOOML() throws InterruptedException {

        ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();

        // 打印线程池的信息
        printStats(threadPool);

        for (int i = 0; i< 100000000; i++) {
            threadPool.execute(() -> {
                String payload = IntStream.rangeClosed(1, 1000000).mapToObj(__ -> "a").collect(Collectors.joining("")) + UUID.randomUUID().toString();

                try {
                    // 休眠一个小时,模拟程序执行耗时一个小时
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                }
            });
        }

        // 线程等待
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    /**
     * @description: 打印线程池信息
     * @params: [threadPool]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/5/8 9:13
     */
    private void printStats(ThreadPoolExecutor threadPool) {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            // 线程池大小
            log.info("Pool Size: {}", threadPool.getPoolSize());
            // 活动线程数
            log.info("Active Threads: {}", threadPool.getActiveCount());
            // 完成的线程数
            log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
            // 队列里的线程数
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());

            log.info("=========================");
        }, 0, 1, TimeUnit.SECONDS);
    }


    /*
        线程池满后拒绝策略详解AbortPolicy(丢弃任务,抛出拒绝异常)
     */
    @GetMapping("right")
    public int right() throws InterruptedException {

        // 使用一个计数器跟踪完成的任务数
        AtomicInteger atomicInteger = new AtomicInteger();

        /*
            自定义一个线程池。这个线程池具有 2 个核心线程、5 个最大线程、使用容量为 10
            就是任务添加到线程池失败会抛出 RejectedExecutionException。此外，我们借助了
            Jodd 类库的 ThreadFactoryBuilder 方法来构造一个线程工厂，实现线程池线程的自定义
            命名。
            线程不报错的最大线程数：最大线程数(5)+阻塞队列大小(10)=15
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").get(),
                new ThreadPoolExecutor.AbortPolicy());

        // 打印线程信息
        printStats(threadPool);

        // 每隔1秒提交一次,一共提交20次任务(相当于循环,i从1变化到20)
        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int id = atomicInteger.incrementAndGet();
            try {
                threadPool.submit(() -> {
                    log.info("{} started", id);
                    // 每个任务耗时10秒
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    log.info("{} finished", id);
                });
            } catch (Exception ex) {
                log.error("error submitting task {}", id, ex);
                atomicInteger.decrementAndGet();
            }
        });

        // 线程休眠1分钟
        TimeUnit.SECONDS.sleep(60);

        // 统计任务数
        return atomicInteger.intValue();
    }

    /*
        线程池拒绝策略详解,更好
     */
    @GetMapping("better")
    public int better() throws InterruptedException {

        // 这里开始是激进线程池的实现
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10) {
            @Override
            public boolean offer(Runnable e) {
                // 先返回false,造成队列满的假象，让线程池优先扩容
                return false;
            }
        };

        // 自定义线程
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2, 5,
                5, TimeUnit.SECONDS,
                queue, new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").get(),
                // 自定义拒绝策略
                (r, executor) -> {
            try {
                //等出现拒绝后再加入队列
                //如果希望队列满了阻塞线程而不是抛出异常,那么可以注释掉下面三行代码,修改为executor.getQueue().put(r);
                if (!executor.getQueue().offer(r, 0, TimeUnit.SECONDS)) {
                    throw new RejectedExecutionException("ThreadPool queue full, failed to offer " + r.toString());
                }
                // executor.getQueue().put(r); //队列慢了进行阻塞
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        //激进线程池实现结束
        // 打印线程信息
        printStats(threadPool);

        //任务编号计数器
        AtomicInteger atomicInteger = new AtomicInteger();

        //每秒提交一个任务，每个任务耗时10秒执行完成，一共提交20个任务
        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int id = atomicInteger.incrementAndGet();
            try {
                threadPool.submit(() -> {
                    log.info("{} started", id);
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    log.info("{} finished", id);
                });
            } catch (Exception ex) {
                log.error("error submitting task {}", id, ex);
                atomicInteger.decrementAndGet();
            }
        });

        TimeUnit.SECONDS.sleep(60);
        return atomicInteger.intValue();
    }

    /*
        我们可以总结出线程池默认的工作行为：
        了解这个策略，有助于我们根据实际的容量规划需求，为线程池设置合适的初始化参数。当
        然，我们也可以通过一些手段来改变这些默认工作行为，比如：

        1、不会初始化 corePoolSize 个线程，有任务来了才创建工作线程；
        2、当核心线程满了之后不会立即扩容线程池，而是把任务堆积到工作队列中；
        3、当工作队列满了后扩容线程池，一直到线程个数达到 maximumPoolSize 为止；
        4、如果队列已满且达到了最大线程后还有任务进来，按照拒绝策略处理；
        5、当线程数大于核心线程数时，线程等待 keepAliveTime 后还是没有任务需要处理的话,收缩线程到核心线程数。


        优化：
            我们有没有办法让线程池更激进一点，优先开启更多的线程，而把队列当成一个后备
            方案呢？比如我们这个例子，任务执行得很慢，需要 10 秒，如果线程池可以优先扩容到 5
            个最大线程，那么这些任务最终都可以完成，而不会因为线程池扩容过晚导致慢任务来不及
            处理。
        大致思路：
        接下来，就请你动手试试看如何实现这样一个“弹性”线程池吧。Tomcat 线程池也实现了
        类似的效果，可供你借鉴。
        务必确认清楚线程池本身是不是复用的
        不久之前我遇到了这样一个事故：某项目生产环境时不时有报警提示线程数过多，超过
        2000 个，收到报警后查看监控发现，瞬时线程数比较多但过一会儿又会降下来，线程数抖
        动很厉害，而应用的访问量变化不大。
        为了定位问题，我们在线程数比较高的时候进行线程栈抓取，抓取后发现内存中有 1000 多
        个自定义线程池。一般而言，线程池肯定是复用的，有 5 个以内的线程池都可以认为正
        常，而 1000 多个线程池肯定不正常。
        在项目代码里，我们没有搜到声明线程池的地方，搜索 execute 关键字后定位到，原来是
        业务代码调用了一个类库来获得线程池，类似如下的业务代码：调用 ThreadPoolHelper
        的 getThreadPool 方法来获得线程池，然后提交数个任务到线程池处理，看不出什么异
        常。来到 ThreadPoolHelper 的实现让人大跌眼镜，getThreadPool 方法居然是每次
        都使用 Executors.newCachedThreadPool 来创建一个线程池

        1、由于线程池在工作队列满了无法入队的情况下会扩容线程池，那么我们是否可以重写队
        列的 offer 方法，造成这个队列已满的假象呢？

        2、由于我们 Hack 了队列，在达到了最大线程后势必会触发拒绝策略，那么能否实现一个
        自定义的拒绝策略处理程序，这个时候再把任务真正插入队列呢？

     */

}
