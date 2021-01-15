package com.java.springboot.servcie;

import com.java.springboot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: Service层
 * @Author: zhangyadong
 * @Date: 2021/1/15 10:48
 * @Version: v1.0
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @description: 添加用户
     * @params: [name, age]
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/15 10:58
     */
    public int insertUser(String name, Integer age) {
        int insertResult = userMapper.insert(name, age);
        log.info("#########insertResult:{}#########",insertResult);
        return insertResult;
    }
}
