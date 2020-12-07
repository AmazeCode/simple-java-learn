package com.java.jvm.deploy;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出问题:java.lang.OutOfMemoryError: Java heap space
 */
public class MemoryOut {

    public static void main(String[] args) {

        /*
            垃圾回收基本原则：内存不足的时候去回收,内存如果足够,暂时不会去回收
         */
        //-Xms1m -Xmx10m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
        List<Object> listObject = new ArrayList<Object>();
        for(int i=0;i<10;i++){
            System.out.println("i:" + i);
            Byte[] bytes = new Byte[1 * 1024 * 1024];
            listObject.add(bytes);
        }
        System.out.println("添加成功........");
    }
}
