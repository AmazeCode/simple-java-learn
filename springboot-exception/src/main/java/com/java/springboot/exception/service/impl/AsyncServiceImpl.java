package com.java.springboot.exception.service.impl;

import com.java.springboot.exception.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    //@Async //相当于这个方法重新开辟了单独的线程进行执行
    @Override
    public String addUserAndEmail() {
        /*log.info("2");
        try {
           Thread.sleep(5000);
        } catch (Exception e){
            log.error("出错了");
        }
        log.info("3");*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("2");
                try {
                    Thread.sleep(5000);
                } catch (Exception e){
                    log.error("出错了");
                }
                log.info("3");
            }
        }).start();
        return "执行成功";
    }

    /*
        @Async 实现原理思路：使用AOP技术在运行时,创建一个单独线程进行执行

     */
}
