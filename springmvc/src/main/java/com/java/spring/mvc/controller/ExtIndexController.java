package com.java.spring.mvc.controller;

import com.java.spring.mvc.annotation.ExtController;
import com.java.spring.mvc.annotation.ExtRequestMapping;

/**
 * @Description: 测试ExtController注解Controller
 * @Author: zhangyadong
 * @Date: 2020/12/29 11:27
 * @Version: v1.0
 */
@ExtController
@ExtRequestMapping("/ext")
public class ExtIndexController {

    @ExtRequestMapping("/test")
    public String test(){
        System.out.println("手写SpringMvc框架。。。。。。");
        return "index";
    }
}
