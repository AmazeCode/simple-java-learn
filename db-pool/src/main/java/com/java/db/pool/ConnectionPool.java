package com.java.db.pool;


import com.java.db.pool.config.DbBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * 白话文阐述数据库连接池实现原理 </br>
 * ########### 核心参数 ############## </br>
 * 1、空闲线程(容器): 没有被使用的连接存放 2、活动线程(容器): 正在使用的连接 </br>
 * ####### 核心步骤 ############## </br>
 * 2.1 初始化线程池（初始化空闲线程）</br>
 * 3.1 调用getConnection方法--获取连接请求 </br>
 * ## 3.1.1 首先去freeConnection获取当前连接,存放在activeConnection </br>
 * 4.1 释放releaseConnection--释放连接(资源回收) </br>
 * ## 4.1.1 获取activeConnection集合连接，转移到freeConnection集合中  </br>
 */
public class ConnectionPool implements IConnectionPool {

    // 使用线程安全集合 空闲线程容器 没有被使用的连接存放
    private List<Connection> freeConnection = new Vector<Connection>();
    // 使用线程安全集合 空闲线程容器 正在使用的连接存放
    private List<Connection> activeConnection = new Vector<Connection>();

    private DbBean dbBean;

    //这里最好使用原子类
    private int countConn = 0;

    public ConnectionPool(DbBean dbBean) {
        //获取配置文件信息
        this.dbBean = dbBean;
        init(dbBean);
    }

    // 初始化线程池（初始化空闲线程）
    private void init(DbBean dbBean){
        if (dbBean == null) {
            return; // 注意最好抛出异常
        }
        // 1、获取初始化连接
        for (int i = 0; i < dbBean.getInitConnections(); i++) {
            //2.创建连接
            Connection connection = newConnection();
            if (connection != null) {
                //3.存放在freeConnection集合
                freeConnection.add(connection);
            }
        }
    }

    // 创建Connection连接  注意：使用count++就一定要加synchronized避免出现线程安全
    private synchronized Connection newConnection() {
        try {
            Class.forName(dbBean.getDriverName());
            Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUserName(), dbBean.getPassword());
            countConn++;//统计创建的连接数
            return connection;
        } catch (Exception e) {
            return null;
        }
    }

    // 调用getConnection方法--获取连接请求
    @Override
    public synchronized Connection getCollection() {
        try {
            Connection connection = null;
            // 思考:怎么知道当前创建连接数>最大连接数
            if (countConn < dbBean.getMaxActiveConnections()){
                // 小于最大活动连接数
                // 1.判断空闲线程是否有数据
                if (freeConnection.size() > 0) {
                    // 空闲线程存在连接 拿到后再删除 == freeConnection.get(0); freeConnection.remove(0);
                    connection = freeConnection.remove(0);
                } else {
                    //创建新的连接
                    connection = newConnection();
                }
                //判断连接是否可用
                isAvailable(connection);

                boolean available = activeConnection.add(connection);
                if (available) {
                    // 往活动线程存
                    activeConnection.add(connection);
                } else {
                    //失败的话，线程数需要释放掉
                    countConn--;
                    connection = getCollection(); // 怎么使用重试? 递归算法
                }
            } else {
                // 大于最大连接数,进行等待
                wait(dbBean.getConnectionTimeOut());
                //重试
                connection = getCollection();
            }
            return connection;
        } catch (Exception e) {
            return null;
        }
    }

    // 判断连接是否可用
    private boolean isAvailable(Connection connection){
        try {
            if (connection == null || connection.isClosed()) {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public synchronized void releaseConnection(Connection connection) {

        try {
            if (isAvailable(connection)) {
                // 判断空闲线程是否大于活动线程 怎么知道空闲线程已经满了。。。。。。
                if (freeConnection.size() < dbBean.getMaxConnections()) {
                    // 空闲线程没有满
                    freeConnection.add(connection);
                } else {
                    // 空闲线程已经满了
                    connection.close();
                }
                activeConnection.remove(connection);
                countConn--;
                notifyAll();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
