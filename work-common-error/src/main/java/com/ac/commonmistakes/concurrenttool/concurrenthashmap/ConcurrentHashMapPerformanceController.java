package com.ac.commonmistakes.concurrenttool.concurrenthashmap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description: 没有充分了解并发工具的特性，从而无法发挥其威力
 * @Author: zhangyadong
 * @Date: 2021/5/7 10:59
 * @Version: v1.0
 */
@Slf4j
@RestController
@RequestMapping("concurrenthashmapformance")
public class ConcurrentHashMapPerformanceController {

    // 循环次数
    private static int LOOP_COUNT = 10000000;
    // 线程数量
    private static int THREAD_COUNT = 10;
    // 元素数量
    private static int ITEM_COUNT = 1000;

    /*
        初始写法,功能上没有什么问题,但是没有显示出ConcurrentHashMap的威力
     */
    private Map<String,Long> normaluse() throws InterruptedException {
        ConcurrentHashMap<String,Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1,LOOP_COUNT).parallel().forEach(i -> {
            //获得一个随机key
            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            synchronized (freqs) {
                if (freqs.containsKey(key)) {
                    // key存在则+1
                    freqs.put(key,freqs.get(key)+1);
                } else {
                    // key不存在则初始化为1
                    freqs.put(key,1L);
                }
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs;
    }

    /*
        升级优化写法
     */
    private Map<String,Long> gooduse() throws InterruptedException {
        // LongAdder线程安全的累加器,因此可以直接调用increment进行进行累加
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1,LOOP_COUNT).parallel().forEach(i -> {
            // 获得一个随机key
            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            // 利用computerIfAbsent()方法来实例化LongAdder,判断key对应的value是否存在,不存在则把lambda表达式的值放入map作为value值
            freqs.computeIfAbsent(key,k -> new LongAdder()).increment();
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1,TimeUnit.HOURS);
        //因为我们的Value是LongAdder而不是Long，所以需要做一次转换才能返回
        return freqs.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().longValue())
                );

    }

    /**
     * @description: 验证结果
     * 结果：
     * 6292536300  089%  normaluse
     * 766471200  011%  gooduse
     * gooduse写法性能明显优于normaluse性能
     */
    @GetMapping("good")
    public String good() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map<String,Long> normaluse = normaluse();
        stopWatch.stop();
        // 校验元素数量
        Assert.isTrue(normaluse.size() == ITEM_COUNT, "normaluse size is error");
        // 校验累计总和
        Assert.isTrue(normaluse.entrySet().stream().mapToLong(item -> item.getValue()).reduce(0, Long::sum) == LOOP_COUNT);
        stopWatch.start("gooduse");
        Map<String,Long> gooduse = gooduse();
        stopWatch.stop();
        Assert.isTrue(gooduse.size() == ITEM_COUNT,"gooduse size error");
        Assert.isTrue(gooduse.entrySet().stream().mapToLong(item -> item.getValue()).reduce(0, Long::sum) == LOOP_COUNT, "gooduse count error");
        log.info(stopWatch.prettyPrint());
        return "OK";
    }
}
