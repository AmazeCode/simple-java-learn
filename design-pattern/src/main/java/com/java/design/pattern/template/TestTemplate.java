package com.java.design.pattern.template;

/**
 * 验证模式结果
 */
public class TestTemplate {

    public static void main(String[] args) {
        MsgTemplate msgTemplate = new YiDong();
        msgTemplate.sendMsg();
    }
}
