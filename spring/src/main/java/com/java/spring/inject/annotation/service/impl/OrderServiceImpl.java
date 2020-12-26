package com.java.spring.inject.annotation.service.impl;

import com.java.spring.inject.annotation.ext.ExtService;
import com.java.spring.inject.annotation.service.OrderService;

@ExtService
public class OrderServiceImpl implements OrderService {

    @Override
    public void addOrder() {
        System.out.println("addOrder");
    }
}
