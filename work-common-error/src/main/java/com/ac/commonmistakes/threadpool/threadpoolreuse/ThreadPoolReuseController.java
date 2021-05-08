package com.ac.commonmistakes.threadpool.threadpoolreuse;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Description: 注意线程池本身是不是复用的: 不要每次都新创建线程池,线程池合理复用
 * @Author: zhangyadong
 * @Date: 2021/5/8 14:22
 * @Version: v1.0
 */
@Slf4j
@RequestMapping("threadpoolreuse")
@RestController
public class ThreadPoolReuseController {

    @GetMapping("wrong")
    public String wrong () throws InterruptedException {

        ThreadPoolExecutor threadPool = ThreadPoolHelper.getRightThreadPool();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            threadPool.execute(() -> {
                String payload = IntStream.rangeClosed(1, 1000000)
                        // mapToObj(__ -> "a") 这种写法是返回1000000个a
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining("")) + UUID.randomUUID().toString();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                // 输出信息
                log.info(payload);
                //log.debug(payload);
            });
        });
        return "OK";
    }

    static class ThreadPoolHelper {

        // 实例化ThreadPoolExecutor
        private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10, 50,2, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").get());

        /*
            错误示例:每次调用都新创建一个线程
         */
        public static ThreadPoolExecutor getThreadPool () {
            // 每次新创建一个线程池
            return (ThreadPoolExecutor) Executors.newCachedThreadPool();
        }

        /*
            正确示例:每次调用使用创建好的线程池,并不是每次重新创建
         */
        public static ThreadPoolExecutor getRightThreadPool () {
            return threadPoolExecutor;
        }
    }
}
