package com.java.design.pattern.facade;

/**
 * 外观设计模式：发送短信服务
 */
public class SmsMsgServiceImpl implements SmsMsgService{

    @Override
    public void send() {
        System.out.println("发送短信。。。。。");
    }
}
