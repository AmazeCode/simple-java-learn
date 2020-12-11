package com.java.design.pattern.factory.abs;

/**
 * 定义抽象工厂：因为比较复杂，不知道如何实现，所以只是定义接口
 */
public interface AbstractFactory {

    //创建发动机
    EngineFactory createEngine();
    //创建座椅
    ChairFactory createChair();
}
