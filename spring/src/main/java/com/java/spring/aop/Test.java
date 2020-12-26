package com.java.spring.aop;

import com.java.spring.annotation.service.UserAnnotationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 测试类
 * @Author: zhangyadong
 * @Date: 2020/12/14 0014 下午 9:45
 * @Version: v1.0
 */
public class Test {

    public static void main(String[] args) {
        //读取spring配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
       /* UserService userService = (UserService)applicationContext.getBean("userServiceImpl");
        userService.add();
        userService.addForAop();*/
        UserAnnotationService userAnnotationService = (UserAnnotationService)applicationContext.getBean("userAnnotationServiceImpl");
        userAnnotationService.add();
    }
}
