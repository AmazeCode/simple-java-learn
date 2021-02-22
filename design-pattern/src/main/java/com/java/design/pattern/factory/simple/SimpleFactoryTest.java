package com.java.design.pattern.factory.simple;

/**
 * @Description: 验证
 * @Author: zhangyadong
 * @Date: 2020/11/26 15:55
 * @Version: v1.0
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {
        //Car car = new BydCar();
        //car.run();
        Car car = CarFactory.createCar("吉利");
        car.run();
    }
}
