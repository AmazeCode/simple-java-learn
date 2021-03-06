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
        这里不能指定具体的事务管理器,原因在于事务已经交给了Atomikos管理
     */
    @Transactional
    public int insertUserTestO1AndTest02(String name, Integer age) {
        int insertResult01 = userMapperTest01.insert(name, age);
        int insertResult02 = userMapperTest02.insert(name, age);
        log.info("#########insertResult:{}#########",insertResult01+insertResult02);
        int i = 1/age;
        int result = insertResult01+insertResult02;
        return result;
    }
}
