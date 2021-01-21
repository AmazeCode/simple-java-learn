package com.java.spring.ioc.annotation.service.impl;

import com.java.spring.ioc.annotation.ext.ExtResource;
import com.java.spring.ioc.annotation.ext.ExtService;
import com.java.spring.ioc.annotation.service.ExtCheckService;
import com.java.spring.ioc.annotation.service.OrderService;

/**
 * @Description: 服务层
 * @Author: zhangyadong
 * @Date: 2020/12/26 12:20
 * @Version: v1.0
 */
@ExtService
public class ExtCheckServiceImpl implements ExtCheckService {

    /*
        注意:@Resource使用的是spring容器,@ExtService使用的自己定义的容器
        使用注定注解时,如果注解没有指定属性名称时会默认为类名小写故orderServiceImpl
     */
    @ExtResource
    private OrderService orderServiceImpl;

    @Override
    public void add() {
        //如果使用@Resource注入不了对象,因为@ExtService是自定义容器存在OrderService,而 @Resource是从spring自带容器中获取的，里面并没有OrderService
        orderServiceImpl.addOrder();
        System.out.println("使用java反射机制初始化对象。。。。。。");
    }
}
