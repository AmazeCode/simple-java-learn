package com.java.mybatis;

import com.java.mybatis.entity.User;
import com.java.mybatis.mapper.UserMapper;
import com.java.mybatis.sql.SqlSession;

/**
 * Mybatis难点攻破
 * 1.mapper接口方法需要和SQL语句进行绑定
 * ##### 思考：接口不能被实例化,那么怎么取调用呢？ #####
 * #### UserMapper 1.使用字节码技术创建虚拟子类 2.使用匿名内部类方式 3.使用动态代理方式创建对象
 */
public class Test {

    public static void main(String[] args) {
        // 使用动态代理技术调用虚拟接口
        UserMapper userMapper = SqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectUser("111",1);
        System.out.println("结果集："+ user.getName() + "," + user.getAge());
        // 先走拦截invoke
        //userMapper.insertUser("111", 1);
    }
}
