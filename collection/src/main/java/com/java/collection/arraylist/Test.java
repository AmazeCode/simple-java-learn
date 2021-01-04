package com.java.collection.arraylist;

import java.util.ArrayList;

/**
 * @Description: 手些ArrayList
 * @Author: zhangyadong
 * @Date: 2021/1/2 23:20
 * @Version: v1.0
 */
public class Test {
    
    /*
        1. 集合底层使用数组实现
        2. 怎么保证数组中存放无限大小 #### 数组扩容技术
     */
    public static void main(String[] args) {
        ExtList extList = new ExtArrayList(1);
        extList.add("dfag");
        extList.add("李四");
        extList.add("dsf");
        extList.add("李四");
        //移除元素
        extList.remove("李四");
        //插入元素到指定位置
        extList.add(1,"王五");
        for (int i = 0; i < extList.getSize(); i++){
            System.out.println(extList.get(i));
        }
    }
}
