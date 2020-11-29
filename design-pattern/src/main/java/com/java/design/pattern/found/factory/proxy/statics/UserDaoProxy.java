package com.java.design.pattern.found.factory.proxy.statics;

/**
 * 静态代理 静态需要生成代理对象,实际开发时不建议使用静态代理
 */
public class UserDaoProxy implements IUserDao{

    private IUserDao iUserDao;

    public UserDaoProxy(IUserDao iUserDao){
        this.iUserDao = iUserDao;
    }

    @Override
    public void add() {
        System.out.println("开启事物");
        iUserDao.add();
        System.out.println("提交事物");
    }

    @Override
    public void save() {
        System.out.println("开启事物");
        iUserDao.save();
        System.out.println("提交事物");
    }
}
