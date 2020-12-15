package com.java.spring.aop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Description: 用户Dao操作数据库
 * @Author: zhangyadong
 * @Date: 2020/12/15 0015 下午 9:18
 * @Version: v1.0
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(String name,Integer age){
        String sql = "INSERT INTO t_user(name,age) VALUES(?,?);";
        int result = jdbcTemplate.update(sql,name,age);
        System.out.println("result:"+result);
    }
}
