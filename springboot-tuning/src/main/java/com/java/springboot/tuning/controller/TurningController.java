package com.java.springboot.tuning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: SpringBoot参数调优
 * @Author: zhangyadong
 * @Date: 2021/1/18 16:41
 * @Version: v1.0
 */
@RestController
public class TurningController {

    @RequestMapping("/turningIndex")
    public String turningIndex() {
        return "turningIndex";
    }
}
