package com.java.collection.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * @Description: HashMap原理
 * @Author: zhangyadong
 * @Date: 2021/1/8 14:59
 * @Version: v1.0
 */
public class Test {

    /*
        hashcode 相同,值对象不一定相同
        equals 相同 hashcode 一定相同
     */
    public static void main(String[] args) {

        // 基于什么原则 后进先出 非公平锁与公平锁
        ExtHashMap map = new ExtHashMap<String,String>();
        //HashMap map = new HashMap<String,String>();
        System.out.println("扩容前：---------------------");
        map.put("1号","aaaa");
        map.put("2号","bbbb");
        map.put("3号","cccc");
        map.put("4号","dddd");
        map.put("5号","eeee");
        map.put("6号","ffff");
        map.put("7号","gggg");
        map.put("8号","hhhh");
        map.put("10号","iiii");
        map.put("11号","jjjj");
        map.put("12号","kkkk");
        map.put("13号","llll");
        map.put("14号","mmmm");
        map.print();
        System.out.println("扩容后:---------------------");
        map.put("15号","mmmm");
        map.print();
        /*
            添加14号之前
            下标位置[0][key:7号,value:jjjj]
            下标位置[1][key:6号,value:ffff]
            下标位置[2][key:5号,value:eeee]
            下标位置[3][key:4号,value:dddd]
            下标位置[4][key:3号,value:cccc]
            下标位置[5][key:2号,value:bbbb]
            下标位置[6][key:1号,value:aaaa]
            下标位置[7]
            下标位置[8]
            下标位置[9]
            下标位置[10]
            下标位置[11]
            下标位置[12]
            下标位置[13]
            下标位置[14]
            下标位置[15]
            添加14号之后
            下标位置[0][key:7号,value:jjjj]
            下标位置[1][key:6号,value:ffff]
            下标位置[2][key:5号,value:eeee]
            下标位置[3][key:4号,value:dddd]
            下标位置[4][key:14号,value:hhhh][key:3号,value:cccc]
            下标位置[5][key:2号,value:bbbb]
            下标位置[6][key:1号,value:aaaa]
            下标位置[7]
            下标位置[8]
            下标位置[9]
            下标位置[10]
            下标位置[11]
            下标位置[12]
            下标位置[13]
            下标位置[14]
            下标位置[15]
         */
    }
}
