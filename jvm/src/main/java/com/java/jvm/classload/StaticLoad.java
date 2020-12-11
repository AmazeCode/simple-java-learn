package com.java.jvm.classload;

/**
 * 静态加载顺序
 */
public class StaticLoad {

    public static void main(String[] args) throws Exception {

        System.out.println("第一次");
        User user = new User();
        user.show();
        Thread.sleep(10000);
        System.out.println("第二次");
        User user1 = new User();
        user1.show();
    }
}
