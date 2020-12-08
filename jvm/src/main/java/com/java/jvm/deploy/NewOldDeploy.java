package com.java.jvm.deploy;

/**
 * @Description: 配置新生代比例大小
 * @Author: zhangyadong
 * @Date: 2020/12/7 0007 下午 8:46
 * @Version: v1.0
 */
public class NewOldDeploy {

    /*
        示例：-Xms20m -Xmx20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC -XX:NewRatio=2
        说明:堆内存初始化值20m,堆内存最大值20m,新生代最大值可用1m,eden空间和from/to空间的比例为2/1
        -XX:SurvivorRatio=2 配置新生代中,eden区以及from区和to区的比例为2:1:1
        -XX:+UseSerialGC 为配置串行回收
        -XX:NewRatio=2 配置新生代与老年代的比例为 1:2
     */
    public static void main(String[] args) {
        //-XX:SurvivorRatio=2 配置新生代中eden from to比例关系2:1:1  默认是8:1:1
        //配置原则,尽量让新生代小点,老年代大点，让垃圾回收在新生代进行
        byte[] b = null;
        for (int i =0; i< 10; i++) {
            b = new byte[1 * 1024 * 1024];
        }
    }
}
