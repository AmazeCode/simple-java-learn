package com.java.design.pattern.template;

/**
 * 移动发送信息
 */
public class YiDong extends MsgTemplate{

    @Override
    public void httpRequest() {
        System.out.println("http://yidong.com");
    }
}
