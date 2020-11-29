package com.java.design.pattern.found.factory.abs;

/**
 * @Description: 抽象工厂
 * @Author: zhangyadong
 * @Date: 2020/11/28 22:32
 * @Version: v1.0
 */
public class AbstraceJiLiFactory implements CarFactory{

    @Override
    public Engine createEngine() {
        return new EngineA();
    }

    @Override
    public Chair createChair() {
        return new ChairA();
    }
}
