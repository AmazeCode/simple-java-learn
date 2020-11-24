package com.java.thread.disruptor.consumer;

import com.java.thread.disruptor.entity.LongEvent;
import com.lmax.disruptor.EventHandler;

/**
 * 消费者获取生产者推送的数据
 */
public class LongEventHandlerTwo implements EventHandler<LongEvent> {

    public void onEvent(LongEvent longEvent, long sequence, boolean andOfBatch) throws Exception{
        System.out.println("消费者2 获取生产者数据...." + longEvent.getValue());
    }
}
