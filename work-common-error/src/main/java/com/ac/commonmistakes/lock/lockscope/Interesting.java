package com.ac.commonmistakes.lock.lockscope;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

/**
 * @Description: 锁实例:基本synchronized使用
 * @Author: zhangyadong
 * @Date: 2021/4/19 0019 下午 9:49
 * @Version: v1.0
 */
@Slf4j
public class Interesting {

    volatile int a = 1;
    volatile int b = 1;

    /**
     * @description: 同步添加方法
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2021/5/8 8:57
     */
    public synchronized void add() {
        log.info("add start");
        for (int i = 0; i < 10000; i++) {
            a++;
            b++;
        }
        log.info("add done");
    }

    /**
     * @description: 非同步比较方法
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2021/5/8 8:56
     */
    public void compare() {
        log.info("compare start");
        for (int i = 0; i < 10000; i++) {
            // a 始终等于b吗？
            if (a < b) {
                log.info("a:{},b:{},{}",a,b,a>b);
                // 最后的a>b应该始终是false吗？
            }
        }
        log.info("compare done");
    }

    /**
     * @description: 同步比较方法
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2021/5/8 8:56
     */
    public synchronized void compareRight() {
        log.info("compare start");
        for (int i = 0; i < 1000000; i++) {
            Assert.assertTrue(a == b);
            if (a < b) {
                log.info("a:{},b:{},{}", a, b, a > b);
            }
        }
        log.info("compare done");
    }

    public static void main(String[] args) {
        /*
            add()、compare() 方法需要同时添加synchronized才能保证线程安全
         */
        Interesting interesting = new Interesting();
        new Thread(() -> interesting.add()).start();
        new Thread(() -> interesting.compare()).start();
    }
}
