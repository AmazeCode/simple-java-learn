package com.ac.commonmistakes.lock.deadlock;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description: 多把锁要小心死锁问题
 * @Author: zhangyadong
 * @Date: 2021/5/6 16:53
 * @Version: v1.0
 */
@Slf4j
@RequestMapping("deadLock")
@RestController
public class DeadLockController {

    private ConcurrentHashMap<String, Item> items = new ConcurrentHashMap<>();

    public DeadLockController() {
        IntStream.range(0, 10).forEach(i -> items.put("item" + i, new Item("item" + i)));
    }

    /**
     * @description: 错误示例
     * 产生死锁的原因:
     * 购物车添加商品的逻辑，随机添加了三种商品，假设一个购物车中的商品
     * 是 item1 和 item2，另一个购物车中的商品是 item2 和 item1，一个线程先获取到了
     * item1 的锁，同时另一个线程获取到了 item2 的锁，然后两个线程接下来要分别获取
     * item2 和 item1 的锁，这个时候锁已经被对方获取了，只能相互等待一直到 10 秒超时
     */
    @GetMapping("wrong")
    public long wrong() {

        long begin = System.currentTimeMillis();

        long success = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> {
                    // 创建购物车
                    List<Item> cart = createCart();
                    // 创建订单
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();
        log.info("success:{} totalRemaining:{} took:{}ms items:{}",
                success,
                items.entrySet().stream().map(item -> item.getValue().remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);
        return success;
    }

    /**
     * @description: 正确示例:避免死锁
     * 解决死锁逻辑:
     * 避免死锁的方案很简单,为购物车中的商品排一下序,让所有的线程一定是先获取
     * item1 的锁然后获取 item2 的锁，就不会有问题了。所以,我只需要修改一行代码,对
     * createCart 获得的购物车按照商品名进行排序即可
     */
    @GetMapping("right")
    public long right() {
        long begin = System.currentTimeMillis();
        long success = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> {
                    // 创建购物车
                    List<Item> cart = createCart().stream()
                            .sorted(Comparator.comparing(Item::getName))
                            .collect(Collectors.toList());
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();
        log.info("success:{} totalRemaining:{} took:{}ms items:{}",
                success,
                items.entrySet().stream().map(item -> item.getValue().remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);
        return success;
    }

    /**
     * @description: 模仿购物车选购商品
     * @params: []
     * @return: java.util.List<com.ac.common.error.code.controller.lock.DeadLockController.Item>
     * @author: zhangyadong
     * @date: 2021/5/6 17:33
     */
    private List<Item> createCart() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> "item" + ThreadLocalRandom.current().nextInt(items.size()))
                .map(name -> items.get(name)).collect(Collectors.toList());
    }

    /**
     * @description: 创建订单
     * @params: [order]
     * @return: boolean
     * @author: zhangyadong
     * @date: 2021/5/6 17:31
     */
    private boolean createOrder (List<Item> order) {
        // 存放所有获得的锁
        List<ReentrantLock> locks = new ArrayList<>();

        for (Item item : order) {
            try {
                // 获得锁10秒超时
                if (item.lock.tryLock(10, TimeUnit.SECONDS)) {
                    locks.add(item.lock);
                } else {
                    locks.forEach(ReentrantLock::lock);
                    return false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 锁全部拿到之后执行扣减库存逻辑
        try {
            order.forEach(item -> item.remaining--);
        } finally {
            locks.forEach(ReentrantLock::lock);
        }
        return true;
    }

    /**
     * @description: 商品实体
     * @params:
     * @return:
     * @author: zhangyadong
     * @date: 2021/5/6 17:27
     */
    @Data
    @RequiredArgsConstructor
    static class Item {
        final String name;
        int remaining = 1000;
        @ToString.Exclude
        ReentrantLock lock = new ReentrantLock();
    }
}
