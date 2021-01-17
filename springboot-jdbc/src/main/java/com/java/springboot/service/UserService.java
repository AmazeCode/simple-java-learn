package com.java.springboot.service;

/**
 * @Description: 用户服务层
 * @Author: zhangyadong
 * @Date: 2021/1/17 0017 下午 4:20
 * @Version: v1.0
 */
public interface UserService {

    /**
     * @description: 创建用户
     * @params: [name, age]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/17 0017 下午 4:21
     */
    void createUser(String name, Integer age);
}
