package com.java.design.pattern.facade;

/**
 * 创建门面
 */
public class Computer {

    EmailMsgService emailMsgService;

    SmsMsgService smsMsgService;

    WeiXinMsgService weiXinMsgService;

    /*
        构造方法
     */
    public Computer() {
        emailMsgService = new EmailMsgServiceImpl();
        smsMsgService = new SmsMsgServiceImpl();
        weiXinMsgService = new WeiXinMsgServiceImpl();
    }

    public void sendMsg(){
        emailMsgService.sendMsg();
        smsMsgService.send();
        weiXinMsgService.send();
    }
}
