package com.java.springboot.actuator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: SpringBoot监控中心(Actuator没有管理页面)
 * @Author: zhangyadong
 * @Date: 2021/1/19 15:33
 * @Version: v1.0
 */
@RestController
public class IndexController {

    @RequestMapping("/indexActuator")
    public String indexActuator(){
        return "indexActuator";
    }
}
