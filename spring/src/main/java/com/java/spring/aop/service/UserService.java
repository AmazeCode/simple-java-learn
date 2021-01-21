package com.java.spring.aop.service;

/**
 * @Description: 用户服务类
 * @Author: zhangyadong
 * @Date: 2020/12/14 0014 下午 9:39
 * @Version: v1.0
 */
public interface UserService {

    /**
     * @description: 手动实现编程事务
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/15 0015 下午 9:43
     */
    void add();

    /**
     * @description: aop实现事务
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/15 0015 下午 10:06
     */
    void addForAop();

    /**
     * @description: 自定义事物注解验证
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/21 15:13
     */
    void addForExtTransaction();
}
