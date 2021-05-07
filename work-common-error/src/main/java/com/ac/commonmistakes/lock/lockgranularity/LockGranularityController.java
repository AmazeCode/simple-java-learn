package com.ac.commonmistakes.lock.lockgranularity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Description: 区分锁的使用场景:确实有一些共享资源需要保护,也要尽可能降低锁的粒度,仅对必要的代码块甚至
 * 是需要保护的资源本身加锁。
 * 比如:在业务代码中,有一个 ArrayList 因为会被多个线程操作而需要保护,又有一段比较
 * 耗时的操作（代码中的 slow 方法）不涉及线程安全问题,应该如何加锁？
 * 错误的做法是,给整段业务逻辑加锁,把slow方法和操作ArrayList的代码同时纳入synchronized代码块;更适合的做法是,把加锁的粒度降到最低,只在操作ArrayList的时候给这个ArrayList加锁。
 * @Author: zhangyadong
 * @Date: 2021/5/6 15:58
 * @Version: v1.0
 */
@Slf4j
@RequestMapping("lockgranularity")
@RestController
public class LockGranularityController {

    private List<Integer> data = new ArrayList<>();

    // 不涉及共享资源的slow方法
    public void slow() {
        try {
            // 休眠10秒
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
        错误示例:
        把无关共享资源逻辑加锁
     */
    @GetMapping("wrong")
    public int wrong () {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1,100).parallel().forEach(i -> {
            // 加锁粒度太粗
            synchronized (this) {
                slow();
                data.add(i);
            }
        });
        log.info("took:{}",System.currentTimeMillis() - begin);
        return data.size();
    }

    /*
        正确示例:
        仅仅加锁共享示例
     */
    @GetMapping("right")
    public int right () {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1,100).parallel().forEach(i -> {
            slow();
            // 只对List加锁
            synchronized (this) {
                data.add(i);
            }
        });
        log.info("took:{}",System.currentTimeMillis() - begin);
        return data.size();
    }
}
