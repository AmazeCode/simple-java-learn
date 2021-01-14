package com.java.springboot.jsp.entity;

/**
 * @Description: 用户类
 * @Author: zhangyadong
 * @Date: 2021/1/14 11:12
 * @Version: v1.0
 */
public class User {

    private int id;
    private String name;
    private String address;

    /**
     * @description: 构造方法
     * @params: [id, name, address]
     * @return: 
     * @author: zhangyadong
     * @date: 2021/1/14 11:14
     */
    public User(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
