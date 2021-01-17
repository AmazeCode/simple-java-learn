package com.java.springboot.service.impl;

import com.java.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description: 实现层
 * @Author: zhangyadong
 * @Date: 2021/1/17 0017 下午 4:21
 * @Version: v1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createUser(String name, Integer age) {
        jdbcTemplate.update("insert into users(name,age) values(?,?);",name,age);
    }
}
