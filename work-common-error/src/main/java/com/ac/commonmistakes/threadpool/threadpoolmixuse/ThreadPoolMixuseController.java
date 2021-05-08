package com.ac.commonmistakes.threadpool.threadpoolmixuse;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * @Description: 正确使用线程池拒绝策略,根据实际情况可进行自定义拒绝策略
 * @Author: zhangyadong
 * @Date: 2021/5/8 14:57
 * @Version: v1.0
 */
@Slf4j
@RequestMapping("threadpoolmixuse")
@RestController
public class ThreadPoolMixuseController {

    /*
        能容纳的最大的线程数: 102个,拒绝策略:CallerRunsPolicy在任务被拒绝添加后，会在调用execute方法的的线程来执行被拒绝的任务,除非executor被关闭，否则任务不会被丢弃。
        说明:
        如果最大线程池设置较小，而且工作队列数不够大，由于代码Bug等导致任务量激增。任务会交个上层线程（主线程）执行，导致主线程既要处理其他任务，
        又要忙碌处理线程池的源源不断的大量任务，导致hang住。进而导致线上故障。(通俗说就是异步线程有可能变为同步)

        因此我们创建新线程池的时候，要考虑线程数量,队列的长度，有使用CachedThreadPool 和 ScheduledThreadPool线程池，
        由于代码bug，导致创建大量的线程，从而导致线上OOM的故障

        -------------------
        因为开启了CallerRunsPolicy 拒绝处理策略，所以当线程满载队列也满的情况下，任务会在提交任务
        的线程，或者说调用 execute 方法的线程执行，也就是说不能认为提交到线程池的任务就
        一定是异步处理的。如果使用了 CallerRunsPolicy 策略，那么有可能异步任务变为同步执
        行。

        或许是测试时发现线程池因为任务处理不过来出
        现了异常，而又不希望线程池丢弃任务，所以最终选择了这样的拒绝策略。不管怎样，这些
        日志足以说明线程池是饱和状态。
     */
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            2, 2,
            1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("batchfileprocess-threadpool-%d").get(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /*
        能容纳的最大的线程数: 102个,拒绝策略: 采用默认策略
     */
    private static ThreadPoolExecutor asyncCalcThreadPool = new ThreadPoolExecutor(
            200, 200,
            1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("asynccalc-threadpool-%d").get());

    /**
     * @description: 打印线程信息
     * @params: [threadPool]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/5/8 15:01
     */
    private void printStats(ThreadPoolExecutor threadPool) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("Pool Size: {}", threadPool.getPoolSize());
            log.info("Active Threads: {}", threadPool.getActiveCount());
            log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());

            log.info("=========================");
        }, 0, 1, TimeUnit.SECONDS);
    }

    /*
        任务
     */
    private Callable<Integer> calcTask() {
        return () -> {
            // 休眠10毫秒
            TimeUnit.MILLISECONDS.sleep(10);
            return 1;
        };
    }

    /**
     * @description: 错误示例
     * @params: []
     * @return: int
     * @author: zhangyadong
     * @date: 2021/5/8 15:29
     */
    @GetMapping("wrong")
    public int wrong() throws ExecutionException, InterruptedException {
        return threadPool.submit(calcTask()).get();
    }

    /**
     * @description: 正确示例
     * @params: []
     * @return: int
     * @author: zhangyadong
     * @date: 2021/5/8 15:29
     */
    @GetMapping("right")
    public int right() throws ExecutionException, InterruptedException {
        return asyncCalcThreadPool.submit(calcTask()).get();
    }

    @PostConstruct// 服务器运行时进行初始化
    public void init() {

        // 打印线程信息
        printStats(threadPool);

        new Thread(() -> {
            // 1_000_000 = 1000000
            String payload = IntStream.rangeClosed(1, 1_000_000)
                    .mapToObj(__ -> "a")
                    .collect(Collectors.joining(""));
            while (true) {
                threadPool.execute(() -> {
                    try {
                        Files.write(Paths.get("demo.txt"), Collections.singletonList(LocalTime.now().toString() + ":" + payload), UTF_8, CREATE, TRUNCATE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    log.info("batch file processing done");
                });
            }
        }).start();
    }
}
