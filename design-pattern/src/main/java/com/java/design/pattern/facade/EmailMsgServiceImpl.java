package com.java.design.pattern.facade;

/**
 *
 */
public class EmailMsgServiceImpl implements EmailMsgService {

    @Override
    public void sendMsg() {
        System.out.println("发送邮件。。。。。。");
    }
}
