package com.java.design.pattern.builder;

/**
 * 建造者模式:游戏的整体、与工厂模式的区别是：建造者模式组成的各个部件是需要关注顺序的
 *
 */
public class Person {

    //头部
    public String head;

    //身体
    public String body;

    //尾部
    public String foot;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }
}
