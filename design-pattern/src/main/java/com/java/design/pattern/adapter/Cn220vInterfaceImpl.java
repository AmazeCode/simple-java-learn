package com.java.design.pattern.adapter;

/**
 * 接口实现
 */
public class Cn220vInterfaceImpl implements Cn220vInterface{

    @Override
    public void connect() {
        System.out.println("中国220v电源接口,开始工作。。。。。");
    }
}
