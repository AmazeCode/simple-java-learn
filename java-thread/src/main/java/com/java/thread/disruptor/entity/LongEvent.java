package com.java.thread.disruptor.entity;

/**
 * 声明一个event，表示生产者与消费者传递数据类型(就是生产者与消费者进行传递的数据类型)
 * 通过Disruptor进行交换的数据类型
 */
public class LongEvent {

    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
