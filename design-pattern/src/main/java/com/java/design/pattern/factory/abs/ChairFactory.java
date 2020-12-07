package com.java.design.pattern.factory.abs;

/**
 * 抽象产品:座椅工厂,让实际供应商实现具体产品
 */
public interface ChairFactory {

    void run();
}

/**
 * 实际供应商
 */
class ChairA implements ChairFactory{

    public void run(){
        System.out.println("自动加热");
    }
}

/**
 * 实际供应商
 */
class ChairB implements ChairFactory{

    public void run(){
        System.out.println("不能加热");
    }
}
