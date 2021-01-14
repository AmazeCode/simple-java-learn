package com.java.springboot.exception.controller;

import com.java.springboot.exception.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 演示springboot异步调用技术
 * @Author: zhangyadong
 * @Date: 2021/1/14 0014 下午 9:02
 * @Version: v1.0
 */
@RestController
@Slf4j
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    // 项目初始化的时候获取
    @Value("${name}")
    private String name;

    @Value("${http_url}")
    private String httpUrl;

    @RequestMapping("/addUserAndEmail")
    public String addUserAndEmail() {
        log.info("1");
        String result = asyncService.addUserAndEmail();
        log.info("4");
        return "result:"+result;
    }

    @RequestMapping("/getName")
    public String getName() {
        return name;
    }

    @RequestMapping("/httpUrl")
    public String httpUrl() {
        return httpUrl;
    }
}
