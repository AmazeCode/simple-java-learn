package com.java.spring.aop.service.impl;

import com.java.spring.aop.dao.UserDao;
import com.java.spring.aop.service.LogService;
import com.java.spring.aop.service.UserService;
import com.java.spring.aop.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogService logService;

    @Autowired
    private TransactionUtil transactionUtil;

    //spring 事务封装？ aop技术
    @Override
    public void add() {
        TransactionStatus transactionStatus = null;
        try{
            //开启事务
            transactionStatus = transactionUtil.begin();
            userDao.add("test001",27);
            int i = 1/0;
            System.out.println("#######编程事务事务###########");
            userDao.add("test002",29);
            //提交事务
            if(transactionStatus != null)
                transactionUtil.commit(transactionStatus);
        }catch (Exception e){
            e.getMessage();
            //回滚事务
            if(transactionStatus != null)
                transactionUtil.rollback(transactionStatus);
        }
    }

    @Override
    public void addForAop() {
        try{
            //注意事项:在使用spring事务的时候,service 不要try,最好将异常抛出给外层aop，异常通知接收回滚
            userDao.add("aop001",27);
            //int i = 1/0;
            System.out.println("#########基于手动事务封装#########");
            userDao.add("aop002",29);
        }catch (Exception e){
            e.getMessage();
            //获取当前事务，直接回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    //@ExtTransaction  //自定义事物注解
    @Transactional
    @Override
    public void addForExtTransaction() {
        logService.addLog();//后面程序发生错误，不能影响到我的回滚   正常当addLog方法执行完毕，就应该提交事物
        userDao.add("test001",21);
        int i = 1/0;
        System.out.println("#################################");
        userDao.add("test002",29);
    }
}
