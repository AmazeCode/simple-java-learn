package com.java.spring.annotation.service.impl;

import com.java.spring.annotation.service.UserAnnotationService;
import com.java.spring.aop.dao.UserDao;
import com.java.spring.aop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 实现类
 * @Author: zhangyadong
 * @Date: 2020/12/20 16:24
 * @Version: v1.0
 */
@Service
public class UserAnnotationServiceImpl implements UserAnnotationService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LogService logService;

    @Override
    //@ExtTransaction
    @Transactional
    public void add() {
        logService.addLog();//后面程序发生错误，不能影响到我的回滚   正常当addLog方法执行完毕，就应该提交事物
        userDao.add("test001",21);
        int i = 1/0;
        System.out.println("#################################");
        userDao.add("test002",29);
    }

    public void del(){
        System.out.println("del");
    }
}
