package com.java.design.pattern.adapter;

/**
 * 电源适配器
 * 1、对象方式（本例子是对象适配器）
 * 2、继承方式extends Cn220vInterfaceImpl
 */
public class PowerAdapter implements Jp110VInterface{

    private Cn220vInterface cn220vInterface;

    /*
        构造方法
     */
    public PowerAdapter(Cn220vInterface cn220vInterface) {
        this.cn220vInterface = cn220vInterface;
    }

    @Override
    public void connect() {
        cn220vInterface.connect();
    }
}
