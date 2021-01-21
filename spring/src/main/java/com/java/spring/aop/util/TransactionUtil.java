package com.java.spring.aop.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * 编程事务 (需要手动 begin、手动提交、手动回滚)
 * 注意：最好不要用单例的，会产生线程安全问题，用原型比较好
 */
@Component
@Scope("prototype") //每个事物都是一个新的实例,不会相互影响.目的解决线程安全问题 多例子
public class TransactionUtil {

    //获取事务源
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    //全局接收事物(spring默认是单例的,会造成线程安全问题，需要把类设置为原型的@Scope("prototype"))
    private TransactionStatus transactionStatus;

    /**
     * @description: 开启事务
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/15 0015 下午 9:48
     */
    public TransactionStatus begin(){
        transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transactionStatus;
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
