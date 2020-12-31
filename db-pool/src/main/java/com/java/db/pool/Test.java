package com.java.db.pool;

import java.sql.Connection;
import java.util.List;
import java.util.Vector;

/**
 * 白话文阐述数据库连接池实现原理 </br>
 * ########### 核心参数 ############## </br>
 * 1、空闲线程(容器): 没有被使用的连接存放 2、活动线程(容器): 正在使用的连接 </br>
 * ####### 核心步骤 ############## </br>
 * 2.1 初始化线程池（初始化空闲线程）</br>
 * 3.1 调用getConnection方法--获取连接请求 </br>
 * ## 3.1.1 首先去freeConnection获取当前连接,存放再activeConnection </br>
 * 4.1 释放releaseConnection--释放连接(资源回收) </br>
 * ## 4.1.1 获取activeConnection集合连接，转移到freeConnection集合中  </br>
 */
public class Test {

    // 使用线程安全集合 空闲线程容器 没有被使用的连接存放
    private List<Connection> freeConnection = new Vector<Connection>();
    // 使用线程安全集合 空闲线程容器 正在使用的连接存放
    private List<Connection> activeConnection = new Vector<Connection>();

    public static void main(String[] args) {
        ThreadConnection threadConnection = new ThreadConnection();
        for (int i = 1; i < 3; i++) {
            Thread thread = new Thread(threadConnection, "线程i:"+i);
            thread.start();
        }
    }
}

class ThreadConnection implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Connection connection = ConnectionPoolManager.getCollection();
            System.out.println(Thread.currentThread().getName() + ",connection:" + connection);
            ConnectionPoolManager.releaseConnection(connection);
        }
    }
}
