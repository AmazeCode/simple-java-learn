package com.java.design.pattern.factory.simple;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 汽车厂(4s店)
 * @Author: zhangyadong
 * @Date: 2020/11/26 15:59
 * @Version: v1.0
 */
public class CarFactory {

    /*
       生产车
     */
    public static Car createCar(String name){

        //为空判断
        if(StringUtils.isEmpty(name)){
            return null;
        }
        if(name.equals("比亚迪")){
            return new BydCar();
        }
        if (name.equals("吉利")){
            return new JiLiCar();
        }
        return null;
    }
}
