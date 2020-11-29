package com.java.design.pattern.proxy.statics;

/**
 * @Description: TODO
 * @Author: zhangyadong
 * @Date: 2020/11/28 22:51
 * @Version: v1.0
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
