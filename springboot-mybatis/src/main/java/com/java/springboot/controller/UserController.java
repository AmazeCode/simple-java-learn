package com.java.springboot.controller;

import com.java.springboot.servcie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 测试springboot整合mybatis
 * @Author: zhangyadong
 * @Date: 2021/1/15 10:58
 * @Version: v1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/insertUser")
    public Integer insertUser(String name, Integer age) {
        return userService.insertUser(name,age);
    }
}
