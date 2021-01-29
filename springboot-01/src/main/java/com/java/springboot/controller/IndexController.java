package com.java.springboot.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 第一个SpringBoot方法,第一个Controller
 * @Author: zhangyadong
 * @Date: 2021/1/12 0012 下午 10:33
 * @Version: v1.0
 */
@RestController
@EnableAutoConfiguration
public class IndexController {

    //微服务情况下，基本上都在类上加上@RestController
    //使用传统方式返回json需要添加@ResponseBody
    //@RestController添加目的： 修饰的类下的所有方法,全部都是返回json格式,这样的话不用每个方法加上@ResponseBody
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/")
    public String indexPage(){
        return "index8080";
    }
    // 如何启动? 第一种启动方式:使用main启动
    // @EnableAutoConfiguration 作用开启自动装配
    public static void main(String[] args) {
        // 告诉SpringBoot 程序入口
        SpringApplication.run(IndexController.class,args);
    }
}
