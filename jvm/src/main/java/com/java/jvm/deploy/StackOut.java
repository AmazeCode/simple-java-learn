package com.java.jvm.deploy;

/**
 * 栈内存溢出
 */
public class StackOut {

    private static int count;

    /*
        -Xss5m 设置最大调用深度为5m
        默认调用深度 11418  配置5m后249174
     */
    public static void count(){
        try{
            count++;
            count();
        }catch (Throwable e){
            System.out.println("最大深度："+ count);
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        count();
    }
}
