package com.java.spring.inject.xml;

import com.java.spring.xml.UserBean;

/**
 * @Description: 测试手些springIoc
 * @Author: zhangyadong
 * @Date: 2020/12/26 11:50
 * @Version: v1.0
 */
public class TestSpringIoc {

    public static void main(String[] args) throws Exception {
        ExtClassPathXmlApplicationContext extClassPathXmlApplicationContext = new ExtClassPathXmlApplicationContext("spring_xml.xml");
        UserBean userBean = (UserBean) extClassPathXmlApplicationContext.getBean("userBean");
    }
}
