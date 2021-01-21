package com.java.spring.aop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Description: Dao操作数据库
 * @Author: zhangyadong
 * @Date: 2020/12/15 0015 下午 9:18
 * @Version: v1.0
 */
@Repository
public class LogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(String name){
        String sql = "INSERT INTO t_log(log_name) VALUES(?);";
        int result = jdbcTemplate.update(sql,name);
        System.out.println("result:"+result);
    }
}
