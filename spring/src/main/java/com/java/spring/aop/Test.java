package com.java.spring.aop;

import com.java.spring.annotation.service.UserAnnotationService;
import com.java.spring.aop.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 依赖注入配置
 * @Author: zhangyadong
 * @Date: 2020/12/14 0014 下午 9:45
 * @Version: v1.0
 */
public class Test {

    public static void main(String[] args) {

        //读取spring配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService)applicationContext.getBean("userServiceImpl");
        userService.add();
        userService.addForAop();
        // 验证
        userService.addForCheckAnnotation();
    }
}
