package com.java.design.pattern.found.factory.proxy.cglib;

import com.java.design.pattern.found.factory.proxy.statics.IUserDao;
import com.java.design.pattern.found.factory.proxy.statics.IUserDaoImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description: TODO
 * @Author: zhangyadong
 * @Date: 2020/11/29 14:44
 * @Version: v1.0
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(Object target){
        this.target = target;
        //操作字节码 生成虚拟子类
        Enhancer enhancer = new Enhancer();
        //表示父类是当前的class
        enhancer.setSuperclass(target.getClass());
        //返回给当前的代理对象
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib-开启事物");
        Object invoke = method.invoke(target,args);
        System.out.println("Cglib-提交事物");
        return invoke;
    }

    /*
        1、CGLIB 没有依赖接口关系。底层使用的是字节码技术
        2、jdk 反射技术
     */
    //日志控制层打印日志 AOP技术，环绕通知之前之后进行拦截参数打印
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        IUserDaoImpl iUserDao = (IUserDaoImpl) cglibProxy.getInstance(new IUserDaoImpl());
        iUserDao.add();
    }
}
