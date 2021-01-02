package com.java.mybatis.sql;

import com.java.mybatis.aop.MyInvocationHandlerMybatis;

import java.lang.reflect.Proxy;

/**
 * @Description: SqlSession
 * @Author: zhangyadong
 * @Date: 2021/1/2 17:20
 * @Version: v1.0
 */
public class SqlSession {

    //加载 Mapper 接口
    public static <T> T getMapper(Class classz){
        return (T) Proxy.newProxyInstance(classz.getClassLoader(), new Class[]{classz}, new MyInvocationHandlerMybatis(classz));
    }
}
