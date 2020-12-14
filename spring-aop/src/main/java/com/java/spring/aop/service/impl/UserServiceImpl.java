package com.java.spring.aop.service.impl;

import com.java.spring.aop.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    //spring 事务封装？ aop技术
    @Override
    public void add() {
        System.out.println("###### 往数据库添加数据.....");
    }
}
