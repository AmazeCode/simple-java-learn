package com.java.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Description: Aop各种通知类型的基本使用
 * @Author: zhangyadong
 * @Date: 2020/12/14 0014 下午 9:09
 * @Version: v1.0
 */
@Component
@Aspect
public class AopLog {

    // aop 编程里面有几个通知； 前置通知、后置通知、运行通知、异常通知、环绕通知
    //前置通知
    @Before("execution(* com.java.spring.aop.service.UserService.add(..))")
    public void before(){
        System.out.println("前置通知 在方法之前执行....");
    }

    //后置通知
    @After("execution(* com.java.spring.aop.service.UserService.add(..))")
    public void after(){
        System.out.println("后置通知 在方法之后执行....");
    }

    //运行通知
    @AfterReturning("execution(* com.java.spring.aop.service.UserService.add(..))")
    public void afterReturning(){
        System.out.println("运行通知");
    }

    //异常通知
    @AfterThrowing("execution(* com.java.spring.aop.service.UserService.add(..))")
    public void afterThrowing(){
        System.out.println("异常通知");
    }

    //环绕通知 在方法之前和之后处理的事情
    @Around("execution(* com.java.spring.aop.service.UserService.add(..))")
    public Object  around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        //调用方法之前执行
        System.out.println("环绕通知调用之前执行");
        //代理调用方法 如果调用方法抛出异常不会执行后面代码,所以一定要抛出异常否则事务不会回滚
        Object object = proceedingJoinPoint.proceed();
        System.out.println("环绕通知调用之后执行");
        return object;
    }
}
