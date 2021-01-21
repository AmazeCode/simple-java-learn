package com.java.spring.ioc.annotation.service.impl;

import com.java.spring.ioc.annotation.ext.ExtService;
import com.java.spring.ioc.annotation.service.OrderService;

@ExtService
public class OrderServiceImpl implements OrderService {

    @Override
    public void addOrder() {
        System.out.println("addOrder");
    }
}
