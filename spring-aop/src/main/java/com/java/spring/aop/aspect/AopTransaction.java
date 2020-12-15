package com.java.spring.aop.aspect;

import com.java.spring.aop.transaction.TransactionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 切面类  基于手动事务封装
 */
@Component
@Aspect
public class AopTransaction {

    @Autowired
    private TransactionUtil transactionUtil;

    //异常通知
    //TransactionUtil最好不要用单例的，如果设置为单例可能会产生线程安全问题，用原型比较好
    @AfterThrowing("execution(* com.java.spring.aop.service.UserService.addForAop(..))")
    public void afterThrowing(){
        System.out.println("事务回滚");
        //获取当前事务,直接回滚
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    //环绕通知 在方法之前和之后处理的事情
    @Around("execution(* com.java.spring.aop.service.UserService.addForAop(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        //调用方法之前执行
        System.out.println("环绕通知调用之前执行");
        TransactionStatus transactionStatus = transactionUtil.begin();
        //代理调用方法 如果调用方法抛出异常不会执行后面代码,所以一定要抛出异常否则事务不会回滚
        Object object = proceedingJoinPoint.proceed();
        System.out.println("环绕通知调用之后执行");
        transactionUtil.commit(transactionStatus);
        return object;
    }
}
