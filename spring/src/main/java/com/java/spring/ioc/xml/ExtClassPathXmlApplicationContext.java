package com.java.spring.ioc.xml;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.List;

/**
 *  手写Spring IoC容器框架,xml版本
 *  1.解析xml文件
 *  2.使用方法参数BeanId查找配置文件中Bean节点的id信息是否一致
 *  3.获取class信息地址，使用反射机制初始化
 */
public class ExtClassPathXmlApplicationContext {

    //xml读取路径地址
    private String xmlPath;

    /**
     * @description: 构造方法
     * @params: [xmlPath]
     * @return:
     * @author: zhangyadong
     * @date: 2021/1/21 13:55
     */
    public ExtClassPathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    /**
     * @description: 获取Bean
     * @params: [beanId]
     * @return: java.lang.Object
     * @author: zhangyadong
     * @date: 2021/1/21 13:54
     */
    public Object getBean(String beanId) throws Exception {
        if(StringUtils.isBlank(beanId)){
            throw new Exception("beanId不能为空!");
        }
        // 1.解析xml文件,获取所有bean的节点信息
        List<Element> readerXml = readerXml();
        if (readerXml == null || readerXml.isEmpty()){
            throw new Exception("配置文件中没有配置bean的信息!");
        }
        // 2.使用方法参数beanid查找配置文件中bean节点的id信息是否一致
        String className = findByElementClass(readerXml,beanId);
        if(StringUtils.isBlank(className)){
            throw new Exception("配置文件中没有配置class地址!");
        }
        // 3.使用反射机制初始化对象
        return newInstance(className);
    }

    /**
     * @description: 使用方法参数BeanId查找配置文件中Bean节点的id信息是否一致.返回class地址
     * @params: [readerXml, beanId]
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2020/12/26 11:40
     */
    public String findByElementClass(List<Element> readerXml, String beanId){
        for(Element element : readerXml){
            //获取属性信息
            String xmlBeanId = element.attributeValue("id");
            if(StringUtils.isBlank(xmlBeanId)){
                continue;
            }

            if (xmlBeanId.equals(beanId)){
                //获取class地址
                String xmlClass = element.attributeValue("class");
                return xmlClass;
            }
        }
        return null;
    }

    //解析xml文件
    public List<Element> readerXml() throws DocumentException {

        //1.解析文件信息
        SAXReader saxReader = new SAXReader();
        //读取xml文件
        Document document = saxReader.read(getResourceAsStream(xmlPath));
        //2.读取根节点
        Element rootElement = document.getRootElement();
        //3.获取根节点下所有的子节点
        List<Element> elements = rootElement.elements();
        return elements;
    }

    /**
     * @description: 初始化对象
     * @params: [className]
     * @return: java.lang.Object
     * @author: zhangyadong
     * @date: 2020/12/26 11:36
     */
    public Object newInstance(String className) throws Exception{
        Class<?> classInfo = Class.forName(className);
        //调用参构造函数
        Constructor<?> constructor = classInfo.getDeclaredConstructor(null);
        //允许访问私有构造函数
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    /**
     * @description: 获取当前上下文路径
     * @params: [xmlPath]
     * @return: java.io.InputStream
     * @author: zhangyadong
     * @date: 2020/12/26 11:17
     */
    public InputStream getResourceAsStream(String xmlPath){
        //this.getClass().getClassLoader()获取根目录即resources
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(xmlPath);
        return inputStream;
    }
}
