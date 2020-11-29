package com.java.design.pattern.found.factory.abs;

/**
 * @Description: 包装厂
 * @Author: zhangyadong
 * @Date: 2020/11/28 22:30
 * @Version: v1.0
 */
public interface CarFactory {

    //创建发动机
    Engine createEngine();
    //创建座椅
    Chair createChair();
}
