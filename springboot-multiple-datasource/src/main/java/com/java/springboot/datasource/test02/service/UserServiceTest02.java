package com.java.springboot.datasource.test02.service;

import com.java.springboot.datasource.test02.mapper.UserMapperTest02;
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
public class UserServiceTest02 {

    @Autowired
    private UserMapperTest02 userMapperTest02;

    /*
        No qualifying bean of type 'org.springframework.transaction.TransactionManager' available:
        expected single matching bean but found 2: test01TransactionManager,test02TransactionManager
        如果@Transactional上不指定具体的事务时,会报错，程序不知道去使用哪个事务
     */

    /**
     * @description: 添加用户
     * @params: [name, age]
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/15 10:58
     */
    @Transactional(transactionManager = "test02TransactionManager")
    public int insertUser(String name, Integer age) {
        int insertResult = userMapperTest02.insert(name, age);
        log.info("#########insertResult:{}#########",insertResult);
        int i = 1/age;
        return insertResult;
    }
}
