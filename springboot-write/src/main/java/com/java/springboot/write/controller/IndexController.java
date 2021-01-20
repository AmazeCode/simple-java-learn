package com.java.springboot.write.controller;

import com.java.springboot.write.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 测试手写springmvc
 * @Author: zhangyadong
 * @Date: 2021/1/20 0020 下午 10:19
 * @Version: v1.0
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index", produces = "text/html;charset=UTF-8")
    public String index() {
        return userService.index();
    }
}
