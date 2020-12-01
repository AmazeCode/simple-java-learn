package com.java.design.pattern.adapter;

/**
 * 电饭煲
 */
public class ElectricCooker {

    private Jp110VInterface jp110VInterface;

    public ElectricCooker(Jp110VInterface jp110VInterface) {
        this.jp110VInterface = jp110VInterface;
    }

    public void cook(){
        jp110VInterface.connect();
        System.out.println("开始做饭。。。。。");
    }
}
