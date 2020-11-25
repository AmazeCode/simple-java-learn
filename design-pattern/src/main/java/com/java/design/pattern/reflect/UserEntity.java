package com.java.design.pattern.reflect;

/**
 * 反射测试实体
 */
public class UserEntity {

    public String userName;

    /**
     * 无参构造函数
     */
    public UserEntity() {
        System.out.println("对象初始化。。。。");
        //throw new RuntimeException();
    }

    /**
     * 有参构造函数
     */
    private UserEntity(String userName) {
        this.userName = userName;
        System.out.println("name:"+userName);
    }
}
