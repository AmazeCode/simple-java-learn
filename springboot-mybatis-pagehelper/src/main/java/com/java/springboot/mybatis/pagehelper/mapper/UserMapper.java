package com.java.springboot.mybatis.pagehelper.mapper;

import com.java.springboot.mybatis.pagehelper.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: Mapper层
 * @Author: zhangyadong
 * @Date: 2021/1/15 10:41
 * @Version: v1.0
 */
public interface UserMapper {

    /**
     * @description: 根据用户名称查询用户信息
     * @params: [name]
     * @return: com.java.springboot.entity.User
     * @author: zhangyadong
     * @date: 2021/1/15 10:46
     */
    @Select("SELECT * FROM users")
    List<User> findUserList();
}
