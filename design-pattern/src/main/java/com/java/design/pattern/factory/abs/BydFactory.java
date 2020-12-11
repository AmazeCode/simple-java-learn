package com.java.design.pattern.factory.abs;

/**
 * 比亚迪工厂:实际工厂来生产这两种产品
 */
public class BydFactory implements AbstractFactory{

    @Override
    public EngineFactory createEngine() {
        return new EngineB();
    }

    @Override
    public ChairFactory createChair() {
        return new ChairB();
    }
}
