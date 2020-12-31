package com.java.db.pool;

import com.java.db.pool.config.DbBean;

import java.sql.Connection;

/**
 * @Description: 管理线程池
 * @Author: zhangyadong
 * @Date: 2020/12/31 19:03
 * @Version: v1.0
 */
public class ConnectionPoolManager {

    private static DbBean dbBean = new DbBean();

    private static ConnectionPool connectionPool = new ConnectionPool(dbBean);


    /**
     * @description: 获取连接(重复利用机制)
     * @params: []
     * @return: java.sql.Connection
     * @author: zhangyadong
     * @date: 2020/12/31 16:13
     */
    public static Connection getCollection(){
        return connectionPool.getCollection();
    }

    /**
     * @description: 释放连接(可回收机制)
     * @params: [connection]
     * @return: java.sql.Connection
     * @author: zhangyadong
     * @date: 2020/12/31 18:49
     */
    public static void releaseConnection(Connection connection){
        connectionPool.releaseConnection(connection);
    }
}
