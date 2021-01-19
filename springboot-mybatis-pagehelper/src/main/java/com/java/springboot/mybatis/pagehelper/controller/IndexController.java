package com.java.springboot.mybatis.pagehelper.controller;

import com.github.pagehelper.PageInfo;
import com.java.springboot.mybatis.pagehelper.entity.User;
import com.java.springboot.mybatis.pagehelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: SpringBoot 整合 pageHelper 测试
 * @Author: zhangyadong
 * @Date: 2021/1/19 0019 下午 10:07
 * @Version: v1.0
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public PageInfo<User> index(int page, int pageSize) {
        return userService.findUserList(page, pageSize);
    }
}
