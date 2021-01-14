package com.java.springboot.exception.service;

import org.springframework.stereotype.Service;

/**
 * @Description: 异步调用服务
 * @Author: zhangyadong
 * @Date: 2021/1/14 0014 下午 9:03
 * @Version: v1.0
 */
public interface AsyncService {

    /**
     * @description: 添加用户的时侯回去发邮件
     * @params: []
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/14 0014 下午 9:05
     */
    public String addUserAndEmail();
}
