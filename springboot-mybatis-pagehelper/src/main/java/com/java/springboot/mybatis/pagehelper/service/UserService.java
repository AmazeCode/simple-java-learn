package com.java.springboot.mybatis.pagehelper.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.springboot.mybatis.pagehelper.entity.User;
import com.java.springboot.mybatis.pagehelper.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: SpringBoot 整合 pageHelper
 * @Author: zhangyadong
 * @Date: 2021/1/19 0019 下午 9:49
 * @Version: v1.0
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @description: 获取用户分页集合 page当前页数 pageSize当前展示多少个
     * @params: [page, pageSize]
     * @return: java.util.List<com.java.springboot.mybatis.pagehelper.entity.User>
     * @author: zhangyadong
     * @date: 2021/1/19 0019 下午 9:51
     */
    public PageInfo<User> findUserList(int page, int pageSize) {
        // 分页：mysql 通过limit; orcale通过伪列; sqlserver通过top
        // pageHelper 帮我们生成分页语句
        PageHelper.startPage(page, pageSize);
        List<User> userList = userMapper.findUserList();
        //实现原理
        // PageInfo<User> userPageInfoList = userMapper.findUserList();
        PageInfo<User> userPageInfoList = new PageInfo<>(userList);
        return userPageInfoList;
    }
}
