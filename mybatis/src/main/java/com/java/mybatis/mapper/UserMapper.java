package com.java.mybatis.mapper;

import com.java.mybatis.annotation.ExtInsert;
import com.java.mybatis.annotation.ExtParam;
import com.java.mybatis.annotation.ExtSelect;
import com.java.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 用户Mapper接口
 * @Author: zhangyadong
 * @Date: 2021/1/2 16:50
 * @Version: v1.0
 */
public interface UserMapper {

    @ExtInsert("insert into t_user(name,age) values(#{userName},#{userAge})")
    public int insertUser(@ExtParam("userName") String userName, @ExtParam("userAge") Integer userAge);

    @ExtSelect("select * from t_user where name=#{name} and age=#{age}")
    User selectUser(@ExtParam("name") String name, @ExtParam("age") Integer age);
}
