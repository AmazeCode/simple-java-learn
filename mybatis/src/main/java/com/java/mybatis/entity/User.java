package com.java.mybatis.entity;

/**
 * @Description: 用户实体类
 * @Author: zhangyadong
 * @Date: 2021/1/2 16:58
 * @Version: v1.0
 */
public class User {

    public String name;

    public Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
