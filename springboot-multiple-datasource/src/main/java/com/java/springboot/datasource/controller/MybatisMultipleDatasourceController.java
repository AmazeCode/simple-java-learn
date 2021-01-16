package com.java.springboot.datasource.controller;

import com.java.springboot.datasource.test01.service.UserServiceTest01;
import com.java.springboot.datasource.test02.service.UserServiceTest02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 多数据源测试
 * @Author: zhangyadong
 * @Date: 2021/1/16 0016 下午 4:39
 * @Version: v1.0
 */
@RestController
public class MybatisMultipleDatasourceController {

    @Autowired
    private UserServiceTest01 userServiceTest01;

    @Autowired
    private UserServiceTest02 userServiceTest02;

    @RequestMapping("/insertUserTest01")
    public Integer insertUserTest01(String name, Integer age) {
        return userServiceTest01.insertUser(name,age);
    }

    @RequestMapping("/insertUserTest02")
    public Integer insertUserTest02(String name, Integer age) {
        return userServiceTest02.insertUser(name,age);
    }

    @RequestMapping("/insertUserTest01AndTest02")
    public Integer insertUserTest01AndTest02(String name, Integer age) {
        return userServiceTest02.insertUser(name,age);
    }
}
