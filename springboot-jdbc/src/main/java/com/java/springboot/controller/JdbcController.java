package com.java.springboot.controller;

import com.java.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: controller
 * @Author: zhangyadong
 * @Date: 2021/1/17 0017 下午 4:25
 * @Version: v1.0
 */
@RestController
public class JdbcController {

    @Autowired
    private UserService userService;

    @RequestMapping("/jdbc")
    public String index(String name,Integer age) {

        userService.createUser(name,age);
        return "jdbc";
    }
}
