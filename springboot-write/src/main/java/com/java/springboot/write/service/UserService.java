package com.java.springboot.write.service;

import org.springframework.stereotype.Service;

/**
 * @Description: 测试手写SpringBoot是否能调通Service
 * @Author: zhangyadong
 * @Date: 2021/1/20 0020 下午 10:53
 * @Version: v1.0
 */
@Service
public class UserService {

    public String index() {
        return "纯手写springboot2.0 加载UserService";
    }
}
