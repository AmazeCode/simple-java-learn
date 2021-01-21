package com.java.spring.ioc.xml;

import com.java.spring.xml.UserBean;

public class TestSpringIoc {

    /**
     * @description: 测试手写Spring容器(IoC)
     * @params: [args]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/21 13:52
     */
    public static void main(String[] args) throws Exception {
        ExtClassPathXmlApplicationContext extClassPathXmlApplicationContext = new ExtClassPathXmlApplicationContext("spring_xml.xml");
        UserBean userBean = (UserBean) extClassPathXmlApplicationContext.getBean("userBean");
    }
}
