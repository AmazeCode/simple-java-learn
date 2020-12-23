package com.java.spring.aop.xml;

/**
 * @Description: 用于验证xml注入bean
 * @Author: zhangyadong
 * @Date: 2020/12/23 0023 下午 10:03
 * @Version: v1.0
 */
public class UserBean {

    private String name;

    private int age;

    private UserBean() {
        System.out.println("无参构造函数。。。。打印输出说明bean加载使用了反射技术");
    }
}
