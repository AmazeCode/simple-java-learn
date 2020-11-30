package com.java.design.pattern.proxy.statics;

/**
 * 定义实现方式
 */
public class IUserDaoImpl implements IUserDao {

    @Override
    public void add() {
        System.out.println(".......add方法.........");
    }

    @Override
    public void save() {
        System.out.println(".......save方法.........");
    }
}
