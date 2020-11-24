package com.java.thread.disruptor.producer;

import com.java.thread.disruptor.entity.LongEvent;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 生产者
 */
public class LongEventProducer {

    private RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer (RingBuffer<LongEvent> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer byteBuffer){
        //获取事件队列下标位置
        long sequ = ringBuffer.next();
        try{
            //取出空队列
            LongEvent longEvent = ringBuffer.get(sequ);
            //给空队列赋值
            longEvent.setValue(byteBuffer.getLong(0));
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            System.out.println("生产者发送数据。。。。。");
            //表示往下表为0的序列位置推数据(注意，一定要放到finally中，避免异常的情况下数据发送不成功)
            ringBuffer.publish(sequ);
        }
    }
}
