package com.java.spring.aop.aspect;

import com.java.spring.aop.annotation.ExtTransaction;
import com.java.spring.aop.annotation.HandTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Method;

/**
 * 自定义事物具体切面实现
 */
@Component //注入到spring中
@Aspect
public class AopExtTransaction {


    @Autowired
    private HandTransaction handTransaction;
    //一个事物实例针对一个事物

    @AfterThrowing("execution(* com.java.spring.aop.annotation.service.*.*.*(..))")
    public void afterThrowing(){
        System.out.println("事务回滚");
        //获取当前事务,直接回滚
        //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        //判断是否开启了事物
        if(handTransaction != null){
            handTransaction.rollback();
        }
    }

    //环绕通知 在方法之前和之后处理的事情
    @Around("execution(* com.java.spring.aop.annotation.service.*.*.*(..))")
    public void around(ProceedingJoinPoint pjp) throws Throwable{

        // 1、获取该方法上是否加上注解
        ExtTransaction extTransaction = getMethodExtTransaction(pjp);
        // 开启事物
        TransactionStatus transactionStatus = begin(extTransaction);
        // 2、调用目标代理对象方法
        pjp.proceed();
        //if(extTransaction != null){//第二次判断是否有注解没意义
        //}
        //3、提交事物
        commit(transactionStatus);
    }

    //重构：提取开启事物逻辑
    private TransactionStatus begin(ExtTransaction extTransaction){
        if (extTransaction == null){
            return null;
        }
        //如果存在事物注解，开启事物
        return handTransaction.begin();
    }

    //重构:提取提交逻辑
    private void commit(TransactionStatus transactionStatus){
        // 判断是否有事物
        if(transactionStatus != null){
            // 如果存在注解提交事物
            handTransaction.commit();
        }
    }

    //重构：把获取方法逻辑单独提取出来
    private ExtTransaction getMethodExtTransaction(ProceedingJoinPoint pjp) throws Exception{
        //获取方法名称
        String methodName = pjp.getSignature().getName();
        //获取目标对象
        Class<?> classTraget = pjp.getTarget().getClass();
        //获取目标对象类型
        Class<?>[] par = ((MethodSignature)pjp.getSignature()).getParameterTypes();
        //获取目标对象方法
        Method objMethod = classTraget.getMethod(methodName,par);
        //拿到事物注解
        ExtTransaction extTransaction = objMethod.getDeclaredAnnotation(ExtTransaction.class);
        return extTransaction;
    }
}
