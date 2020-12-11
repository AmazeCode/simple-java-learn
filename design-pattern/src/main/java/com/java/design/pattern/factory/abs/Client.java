package com.java.design.pattern.factory.abs;

/**
 * @Description: 验证
 * @Author: zhangyadong
 * @Date: 2020/11/28 22:33
 * @Version: v1.0
 */
public class Client {

    public static void main(String[] args) {
        AbstractFactory carFactory = new JiLiFactory();
        EngineFactory engine = carFactory.createEngine();
        ChairFactory chair = carFactory.createChair();
        engine.run();
        chair.run();
    }
}
