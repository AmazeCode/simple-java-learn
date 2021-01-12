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
        System.out.println("扩容前：---------------------");
        map.put("1号", "1号");
        map.put("2号","2号");
        map.put("3号","3号");
        map.put("4号","4号");
        map.put("6号","6号");
        map.put("7号","7号");
        map.put("14号","14号");

        map.put("22号","22号");
        map.put("26号","26号");
        map.put("27号","27号");
        map.put("28号","28号");
        map.put("66号","66号");
        map.put("30号","30号");
        map.print();
        System.out.println("扩容后:---------------------");
        map.put("31号","31号");
        map.put("66号", "689号");
        map.print();
        map.get("66号");
        /*
            扩容前：---------------------
            下标位置[0][key:7号,value:7号]
            下标位置[1][key:28号,value:28号][key:6号,value:6号]
            下标位置[2][key:27号,value:27号]
            下标位置[3][key:26号,value:26号][key:4号,value:4号]
            下标位置[4][key:14号,value:14号][key:3号,value:3号]
            下标位置[5][key:2号,value:2号]
            下标位置[6][key:1号,value:1号]
            下标位置[7][key:66号,value:66号][key:22号,value:22号]
            下标位置[8]
            下标位置[9]
            下标位置[10][key:30号,value:30号]
            下标位置[11]
            下标位置[12]
            下标位置[13]
            下标位置[14]
            下标位置[15]
            扩容后:---------------------
            下标位置[0][key:7号,value:7号]
            下标位置[1][key:6号,value:6号]
            下标位置[2]
            下标位置[3][key:4号,value:4号]
            下标位置[4][key:3号,value:3号]
            下标位置[5][key:2号,value:2号]
            下标位置[6][key:1号,value:1号]
            下标位置[7]
            下标位置[8]
            下标位置[9]
            下标位置[10]
            下标位置[11]
            下标位置[12]
            下标位置[13]
            下标位置[14]
            下标位置[15]
            下标位置[16]
            下标位置[17][key:28号,value:28号]
            下标位置[18][key:27号,value:27号]
            下标位置[19][key:26号,value:26号]
            下标位置[20][key:14号,value:14号]
            下标位置[21]
            下标位置[22]
            下标位置[23][key:22号,value:22号][key:66号,value:66号]
            下标位置[24]
            下标位置[25][key:31号,value:31号]
            下标位置[26][key:30号,value:30号]
            下标位置[27]
            下标位置[28]
            下标位置[29]
            下标位置[30]
            下标位置[31]
         */
    }
}
