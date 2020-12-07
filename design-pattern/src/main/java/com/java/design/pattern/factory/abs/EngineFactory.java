package com.java.design.pattern.factory.abs;

/**
 * 抽象产品:发动机工厂,让实际供应商实现具体产品
 */
public interface EngineFactory {

    void run();
}

/**
 * 实际供应商
 */
class EngineA implements EngineFactory{
    public void run(){
        System.out.println("发动机转速快。");
    }
}

/**
 * 实际供应商
 */
class EngineB implements EngineFactory{
    public void run(){
        System.out.println("发动机转速慢。");
    }
}
