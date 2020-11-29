package com.java.design.pattern.found.factory.abs;

/**
 * @Description: 发动机
 * @Author: zhangyadong
 * @Date: 2020/11/28 22:25
 * @Version: v1.0
 */
public interface Engine {

    void run();
}

class EngineA implements Engine{
    public void run(){
        System.out.println("发动机转速快。");
    }
}

class EngineB implements Engine{
    public void run(){
        System.out.println("发动机转速慢。");
    }
}
