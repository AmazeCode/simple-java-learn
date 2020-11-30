package com.java.design.pattern.factory.abs;

/**
 * 吉利工厂:实际工厂来生产这两种产品
 */
public class JiLiFactory implements AbstractFactory {

    @Override
    public Engine createEngine() {
        return new EngineA();
    }

    @Override
    public Chair createChair() {
        return new ChairA();
    }
}
