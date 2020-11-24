package com.java.thread.disruptor;

import com.java.thread.disruptor.consumer.LongEventHandler;
import com.java.thread.disruptor.consumer.LongEventHandlerTwo;
import com.java.thread.disruptor.entity.LongEvent;
import com.java.thread.disruptor.factory.LongEventFactory;
import com.java.thread.disruptor.producer.LongEventProducer;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 运行主函数
 * Disruptor使用场景：是一个生产者多个消费者场景，如果LinkedBlockingQueue出现性能问题时，可以考虑Disruptor
 */
public class DisruptorMain {

    /*
        可参考美团技术文章:https://tech.meituan.com/2016/11/18/disruptor.html
     */
    public static void main(String[] args) {

        //1、创建可缓存线程池,提供给consumer
        ExecutorService executor = Executors.newCachedThreadPool();
        //2、创建一个工厂,使用父类进行接收
        EventFactory<LongEvent> longEventEventFactory = new LongEventFactory();
        //3、创建ringBuffer大小
        int ringBufferSzie = 1024*1024;//一定要是2的N次方
        //4、创建Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(longEventEventFactory,ringBufferSzie,executor, ProducerType.MULTI,new YieldingWaitStrategy());
        /*
            一个生产者,多个消费者,默认重复消费,可以配置分组,去规定是否均分消费以及哪个消费者消费多少个
         */
        //5、连接消费者----注册消费者(往哪个消费者投递信息)
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.handleEventsWith(new LongEventHandlerTwo());

        //6、启动
        disruptor.start();
        //7、创建ringBuffer容器
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //8、创建生产者
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        //9、指定缓冲区大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for(int i=0;i<100;i++){
            //对应生产者第0个位置
            byteBuffer.putLong(0,i);
            producer.onData(byteBuffer);
        }
        //关闭线程池
        executor.shutdown();
    }
}
