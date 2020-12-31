package com.java.db.pool;

import java.sql.Connection;

/**
 * @Description: 连接数据库池
 * @Author: zhangyadong
 * @Date: 2020/12/31 16:12
 * @Version: v1.0
 */
public interface IConnectionPool {

    /**
     * @description: 获取连接(重复利用机制)
     * @params: []
     * @return: java.sql.Connection
     * @author: zhangyadong
     * @date: 2020/12/31 16:13
     */
    public Connection getCollection();

    /**
     * @description: 释放连接(可回收机制)
     * @params: [connection]
     * @return: java.sql.Connection
     * @author: zhangyadong
     * @date: 2020/12/31 18:49
     */
    public void releaseConnection(Connection connection);
}
