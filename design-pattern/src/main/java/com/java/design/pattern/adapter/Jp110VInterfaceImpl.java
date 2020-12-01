package com.java.design.pattern.adapter;

/**
 * 接口实现
 */
public class Jp110VInterfaceImpl implements Jp110VInterface {

    @Override
    public void connect() {
        System.out.println("日本110v电源接口,开始工作。。。。。");
    }
}
