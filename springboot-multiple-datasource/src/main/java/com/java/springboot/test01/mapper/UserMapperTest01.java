package com.java.springboot.test01.mapper;

import com.java.springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description: Mapper层
 * @Author: zhangyadong
 * @Date: 2021/1/15 10:41
 * @Version: v1.0
 */
public interface UserMapperTest01 {

    /**
     * @description: 根据用户名称查询用户信息
     * @params: [name]
     * @return: com.java.springboot.entity.User
     * @author: zhangyadong
     * @date: 2021/1/15 10:46
     */
    @Select("SELECT * FROM users WHERE NAME = #{name}")
    User findUserByName(@Param("name") String name);

    /**
     * @description: 添加用户信息
     * @params: [name, age]
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/15 10:46
     */
    @Insert("insert into users (name,age) values(#{name},#{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);
}
