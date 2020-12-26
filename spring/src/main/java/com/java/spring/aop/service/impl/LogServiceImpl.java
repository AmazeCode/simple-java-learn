package com.java.spring.aop.service.impl;

import com.java.spring.aop.dao.LogDao;
import com.java.spring.aop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 实现层
 * @Author: zhangyadong
 * @Date: 2020/12/20 17:07
 * @Version: v1.0
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    /*
        如果要验证传播行为需要开启事物注解，同时需要注解掉AopExtTransaction。不然会被影响
        事物的传播行为(7种)：(所说的当前事物都是指的外层service方法的事物)
        PROPAGATION_REQUIRED:如果当前有事物，就用当前事物，如果当前没事物，就新建一个事物。(这个是最常见的)
        PROPAGATION_SUPPORTS:支持当钱事物，如果当前没有事物，就以非事物方式进行。//如果外层方法没有事物，内层被调用的service方法就以非事物进行执行
        PROPAGATION_MANDATORY:支持当前事物，如果当前没有事物，就抛出异常   //支持外层事物，如果外层没有事物，就抛出异常
        PROPAGATION_REQUIRES_NEW:新建事物，如果当前事物存在，就把当前事物挂起  //内层新建事物，如果外层事物存在就把外层事物挂起
        PROPAGATION_NOT_SUPPORTED：以非事物方式执行操作，如果当前事物存在，就把当前事物挂起//内层service方法以非事物执行，如果外层事物存在就把当前事物挂起
        PROPAGATION_NEVER:以非事物方式执行，如果当前存在事物，则抛出异常 //内层service方法以非事物执行，如果外层事物存在就抛出异常
        PROPAGATION_NESTED：如果一个活动的事务存在，则运行在一个嵌套的事务中。 如果没有活动事务, 则按TransactionDefinition.PROPAGATION_REQUIRED 属性执行
        注意：PROPAGATION_NESTED 与PROPAGATION_REQUIRES_NEW的区别:
        它们非常类似,都像一个嵌套事务，如果不存在一个活动的事务，都会开启一个新的事务。
        使用 PROPAGATION_REQUIRES_NEW时，内层事务与外层事务就像两个独立的事务一样，一旦内层事务进行了提交后，外层事务不能对其进行回滚。两个事务互不影响。
        两个事务不是一个真正的嵌套事务。同时它需要JTA事务管理器的支持。

        使用PROPAGATION_NESTED时，外层事务的回滚可以引起内层事务的回滚。而内层事务的异常并不会导致外层事务的回滚，它是一个真正的嵌套事务，它是一个真正的嵌套事务。
        但是只有外层事物执行完提交后内层事物才会提交
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addLog() {
        logDao.add("addLog"+System.currentTimeMillis());
    }
}
