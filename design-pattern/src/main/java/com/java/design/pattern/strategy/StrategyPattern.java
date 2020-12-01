package com.java.design.pattern.strategy;

/**
 * 策略模式
 */
public class StrategyPattern {

    public static void main(String[] args) {

    }
    // 蚂蚁课堂会员分为 3种级别 铂金会员 黄金会员 普通会员
    public void getPrice(String level){
        Long price = null;
        if(level.equals("铂金会员")){
            //只需要100元
           price = (long)100 * 100;
        }else if(level.equals("黄金会员")){
            price = (long)100 * 150;
        }else if(level.equals("普通会员")){
            price = (long)100 * 200;
        }
    }
}
