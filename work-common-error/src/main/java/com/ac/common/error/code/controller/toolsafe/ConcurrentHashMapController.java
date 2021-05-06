package com.ac.common.error.code.controller.toolsafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * 使用了线程安全的并发工具，并不代表解决了所有线程安全问题
 * JDK 1.5 后推出的 ConcurrentHashMap，是一个高性能的线程安全的哈希表容器。“线
 * 程安全”这四个字特别容易让人误解，因为 ConcurrentHashMap 只能保证提供的原子
 * 性读写操作是线程安全的,并不能保证线程池线程的安全性
 * @Author: zhangyadong
 * @Date: 2021/4/16 14:50
 * @Version: v1.0
 */
@Slf4j
@RestController
@RequestMapping("concurrenthashmap")
public class ConcurrentHashMapController {

    // 线程个数
    private static int THREAD_COUNT = 10;
    // 总元素数量
    private static int ITEM_COUNT = 1000;

    // 帮助方法,用来获得一个指定元素数量模拟数据的ConcurrentHashMap
    private ConcurrentHashMap<String,Long> getData(int count) {
        return LongStream.rangeClosed(1, count).boxed().collect(Collectors.toConcurrentMap(i -> UUID.randomUUID().toString(),
                // 输入什么就输出什么
                Function.identity(),
                // 键冲突时保留现有的值,o1
                (o1,o2) -> o1, ConcurrentHashMap::new));
    }

    /*
        反例：
        2021-04-16 15:29:10.348  INFO 10060 --- [nio-8080-exec-1] c.a.c.e.c.c.ConcurrentHashMapController  : init size:900
        2021-04-16 15:29:42.843  INFO 10060 --- [Pool-2-worker-9] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:100
        2021-04-16 15:29:43.386  INFO 10060 --- [ool-2-worker-11] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:100
        2021-04-16 15:29:43.388  INFO 10060 --- [Pool-2-worker-4] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:61
        2021-04-16 15:29:43.392  INFO 10060 --- [ool-2-worker-15] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:28
        2021-04-16 15:29:43.391  INFO 10060 --- [Pool-2-worker-8] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:28
        2021-04-16 15:29:43.392  INFO 10060 --- [Pool-2-worker-2] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:52
        2021-04-16 15:29:43.387  INFO 10060 --- [ool-2-worker-13] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:92
        2021-04-16 15:29:43.390  INFO 10060 --- [Pool-2-worker-6] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:54
        2021-04-16 15:29:43.393  INFO 10060 --- [Pool-2-worker-9] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:29:43.393  INFO 10060 --- [ool-2-worker-10] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:29:44.915  INFO 10060 --- [nio-8080-exec-1] c.a.c.e.c.c.ConcurrentHashMapController  : finish size:1415
        显示的异常结果
     */
    @GetMapping("wrong")
    public String wrong() throws InterruptedException {
        ConcurrentHashMap<String,Long> concurrentHashMap = getData(ITEM_COUNT-100);
        log.info("init size:{}", concurrentHashMap.size());
        
        // ForkJoinPool将一个任务拆分成多个小任务,并把小人物处理结果汇总到一个结果上
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        // 使用线程池并发处理逻辑
        forkJoinPool.execute(() -> IntStream.rangeClosed(1,10).parallel().forEach(i -> {
            // 查询还需要补充多少元素
            int gap = ITEM_COUNT - concurrentHashMap.size();
            log.info("gap size:{}", gap);
            // 补充元素
            concurrentHashMap.putAll(getData(gap));
        }));
        // 等待所有任务完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        // 最后元素个数会是1000吗?
        log.info("finish size:{}", concurrentHashMap.size());
        return "Ok";
    }


    /*
        正例:
        使用了ConcurrentHashMap,不代表对它的多个操作之间的状态是一致的,是没有其他线程在操作它的,如果需要确保需要手动加锁。
        2021-04-16 15:37:34.649  INFO 14236 --- [nio-8080-exec-1] c.a.c.e.c.c.ConcurrentHashMapController  : init size:900
        2021-04-16 15:37:34.658  INFO 14236 --- [Pool-1-worker-9] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:100
        2021-04-16 15:37:34.695  INFO 14236 --- [Pool-1-worker-2] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.695  INFO 14236 --- [ool-1-worker-11] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.696  INFO 14236 --- [ool-1-worker-11] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.696  INFO 14236 --- [ool-1-worker-11] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.696  INFO 14236 --- [Pool-1-worker-2] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.696  INFO 14236 --- [Pool-1-worker-9] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.696  INFO 14236 --- [Pool-1-worker-2] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.697  INFO 14236 --- [ool-1-worker-11] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.697  INFO 14236 --- [Pool-1-worker-9] c.a.c.e.c.c.ConcurrentHashMapController  : gap size:0
        2021-04-16 15:37:34.718  INFO 14236 --- [nio-8080-exec-1] c.a.c.e.c.c.ConcurrentHashMapController  : finish size:1000
     */
    @GetMapping("right")
    public String right() throws InterruptedException {
        ConcurrentHashMap<String,Long> concurrentHashMap = getData(ITEM_COUNT-100);
        log.info("init size:{}", concurrentHashMap.size());

        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        // 使用线程池并发处理逻辑
        forkJoinPool.execute(() -> IntStream.rangeClosed(1,10).parallel().forEach(i -> {
            // 下面的这段复合逻辑需要锁一下这个ConcurrentHashMap
            synchronized (concurrentHashMap) {
                // 查询还需要补充多少元素
                int gap = ITEM_COUNT - concurrentHashMap.size();
                log.info("gap size:{}", gap);
                // 补充元素
                concurrentHashMap.putAll(getData(gap));
            }
        }));
        // 等待所有任务完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        // 最后元素个数会是1000吗?
        log.info("finish size:{}", concurrentHashMap.size());
        return "Ok";
    }
}
