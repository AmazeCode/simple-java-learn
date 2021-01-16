package com.java.springboot.test01.service;

import com.java.springboot.test01.mapper.UserMapperTest01;
import com.java.springboot.test02.mapper.UserMapperTest02;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: Service层
 * @Author: zhangyadong
 * @Date: 2021/1/15 10:48
 * @Version: v1.0
 */
@Service
@Slf4j
public class UserServiceTest01 {

    @Autowired
    private UserMapperTest01 userMapperTest01;

    @Autowired
    private UserMapperTest02 userMapperTest02;

    /*
        能否自动实现多数据源注解@MultipleDataSource(ref = "dataSource01“)实现指定书屋管理器？
     */

    /**
     * @description: 添加用户
     * @params: [name, age]
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/15 10:58
     */
    @Transactional(transactionManager = "test01TransactionManager") //需要指定事务管理器
    public int insertUser(String name, Integer age) {
        int insertResult = userMapperTest01.insert(name, age);
        log.info("#########insertResult:{}#########",insertResult);
        int i = 1/age;
        return insertResult;
    }


    /*
        一个方法涉及两个数据源操作:传统的分布式事务解决方案 jta+atomikos注册同一个全局事务中
     */
    @Transactional(transactionManager = "test01TransactionManager") //需要指定事务管理器
    public int insertUserTestO1AndTest02(String name, Integer age) {
        int insertResult01 = userMapperTest01.insert(name, age);
        int insertResult02 = userMapperTest02.insert(name, age);
        log.info("#########insertResult:{}#########",insertResult01+insertResult02);
        int i = 1/age;
        int result = insertResult01+insertResult02;
        return result;
    }
}
