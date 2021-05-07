package com.ac.commonmistakes.lock.lockscope;

import lombok.Getter;

/**
 * @Description: 锁使用实体类
 * @Author: zhangyadong
 * @Date: 2021/4/20 10:54
 * @Version: v1.0
 */
public class Data {

    // 静态counter属于类,在多个实例中共享
    @Getter
    private static int counter = 0;

    public static int reset() {
        counter = 0;
        return counter;
    }

    /*
        另外一种修改方法是wrong直接写成静态,但我们不可能为了解决线程安全问题改变代码结构，把实例方法改为静态方法。
     */
    public synchronized void wrong() {
        counter++;
    }

    /*
        修改方式：同样定义一个Object类型的静态字段,在操作counter之前对这个字段加锁(要锁定static的变量要使用类级别锁)
     */
    private static Object locker = new Object();

    public void right() {
        synchronized (locker) {
            counter++;
        }
    }
}
