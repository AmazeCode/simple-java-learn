package com.java.spring.aop.xml;

import com.java.spring.aop.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: Xml注入
 * @Author: zhangyadong
 * @Date: 2020/12/23 0023 下午 9:15
 * @Version: v1.0
 */
public class XmlAnnotation {

    public static void main(String[] args) {
        //SpringIoc XML版本使用dom4j+反射技术
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring_xml.xml");
        System.out.println("================================");
        UserBean userBean = (UserBean)app.getBean("userBean");
        System.out.println(userBean);

        /*
            无参构造函数执行在”========“输出打印之前,说明加载spring配置文件的时候就进行了bean初始化
         */
    }
}
