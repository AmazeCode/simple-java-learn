package com.java.design.pattern.found.factory.proxy.jdk;

import com.java.design.pattern.found.factory.proxy.statics.IUserDao;
import com.java.design.pattern.found.factory.proxy.statics.IUserDaoImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  JDK动态代理实现
 *  每次生成动态代理对象时，实现了InvocationHandler接口的调用处理对象
 */
public class InvocationHandlerImpl implements InvocationHandler {

    private Object target;//传入目标代理对象

    public InvocationHandlerImpl(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //开启事物
        System.out.println("动态代理-开启事物");
        Object invoke = method.invoke(target,args);
        System.out.println("动态代理-结束事物");
        return invoke;
    }

    public static void main(String[] args) throws Exception{
        IUserDao iUserDao = new IUserDaoImpl();
        InvocationHandlerImpl invocationHandler = new InvocationHandlerImpl(iUserDao);
        //获取类加载器
        ClassLoader classLoader = iUserDao.getClass().getClassLoader();
        //获取当前实现的接口(用于判断类是否实现了接口)
        Class<?>[] face = iUserDao.getClass().getInterfaces();
        //调用动态代理实例
        IUserDao iUserDao1 = (IUserDao) Proxy.newProxyInstance(classLoader,face,invocationHandler);
        iUserDao1.add();
    }
}
