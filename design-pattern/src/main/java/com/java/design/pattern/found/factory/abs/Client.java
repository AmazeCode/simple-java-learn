package com.java.design.pattern.found.factory.abs;

/**
 * @Description: 验证
 * @Author: zhangyadong
 * @Date: 2020/11/28 22:33
 * @Version: v1.0
 */
public class Client {

    public static void main(String[] args) {
        CarFactory carFactory = new AbstraceJiLiFactory();
        Engine engine = carFactory.createEngine();
        Chair chair = carFactory.createChair();
        engine.run();
        chair.run();
    }
}
