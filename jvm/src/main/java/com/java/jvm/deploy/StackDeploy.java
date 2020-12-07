package com.java.jvm.deploy;

/**
 * @Description: 堆内存大小配置
 * @Author: zhangyadong
 * @Date: 2020/12/7 0007 下午 8:22
 * @Version: v1.0
 */
public class StackDeploy {

    public static void main(String[] args) {
        //-Xmx20m -Xms5m    表示最大内存20m，初始内存5m

        byte[] b = new byte[4 * 1024 * 1024];
        System.out.println("分配4M空间给数组");
        System.out.println("最大内存" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
        System.out.println("可用内存" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M");
        System.out.println("已经使用内存" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M");
    }
}
