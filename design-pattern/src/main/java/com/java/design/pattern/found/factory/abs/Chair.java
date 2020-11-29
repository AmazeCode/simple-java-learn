package com.java.design.pattern.found.factory.abs;

/**
 * @Description: 座椅
 * @Author: zhangyadong
 * @Date: 2020/11/28 22:28
 * @Version: v1.0
 */
public interface Chair {

    void run();
}

class ChairA implements Chair{

    public void run(){
        System.out.println("自动加热");
    }
}

class ChairB implements Chair{

    public void run(){
        System.out.println("不能加热");
    }
}
