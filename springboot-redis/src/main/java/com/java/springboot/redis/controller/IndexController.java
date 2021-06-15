package com.java.springboot.redis.controller;

import com.java.springboot.redis.pojo.User;
import com.java.springboot.redis.util.ControlRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Description: IndexController
 * @Author: zhangyadong
 * @Date: 2021/6/7 0007 下午 9:42
 * @Version: v1.0
 */
@RestController
public class IndexController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
}
