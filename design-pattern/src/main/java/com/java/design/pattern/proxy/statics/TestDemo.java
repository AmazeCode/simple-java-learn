package com.java.design.pattern.proxy.statics;

/**
 * @Description: 验证
 * @Author: zhangyadong
 * @Date: 2020/11/28 22:53
 * @Version: v1.0
 */
public class TestDemo {

    public static void main(String[] args) {
        IUserDao iUserDao = new IUserDaoImpl();
        UserDaoProxy userDaoProxy = new UserDaoProxy(iUserDao);
        userDaoProxy.add();
    }
}
