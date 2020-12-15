package com.java.spring.aop.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * 编程事务 (需要手动 begin、手动提交、手动回滚)
 * 注意：最好不要用单例的，会产生线程安全问题，用原型比较好
 */
@Component
public class TransactionUtil {

    //获取事务源
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * @description: 开启事务
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/15 0015 下午 9:48
     */
    public TransactionStatus begin(){
        TransactionStatus status = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return status;
    }

    //提交事务
    public void commit(TransactionStatus transactionStatus){
        dataSourceTransactionManager.commit(transactionStatus);
    }

    //回滚事务
    public void rollback(TransactionStatus transactionStatus){
        dataSourceTransactionManager.rollback(transactionStatus);
    }
}
