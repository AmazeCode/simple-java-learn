package com.java.spring.xml;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: Xml解析实际
 * @Author: zhangyadong
 * @Date: 2020/12/24 0024 下午 9:53
 * @Version: v1.0
 */
public class XmlAnaly {

    /**
     * @description: Xml解析测试
     * @params: [args]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/21 11:31
     */
    public static void main(String[] args) throws Exception{
        new XmlAnaly().xmlAnalyse();
    }

    public void xmlAnalyse() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        //读取xml文件
        Document document = saxReader.read(getResourceAsStream("student.xml"));
        //获取根节点
        Element element = document.getRootElement();
        getNodes(element);
    }

    public void getNodes(Element rootElement){
        System.out.println("获取节点名称"+rootElement.getName());
        //获取属性方法
        List<Attribute> attributes = rootElement.attributes();
        for (Attribute att : attributes){
            System.out.println(att.getName()+"-------"+att.getText());
        }
        //获取属性value
        String textTrim = rootElement.getTextTrim();
        if(StringUtils.isNotEmpty(textTrim)){
            System.out.println("textTrim："+textTrim);
        }
        //使用迭代器获取子节点信息(递归调用)
        Iterator<Element> iterator = rootElement.elementIterator();
        while(iterator.hasNext()){
            Element next = iterator.next();
            getNodes(next);
        }
    }

    public InputStream getResourceAsStream(String xmlPath){
        //this.getClass().getClassLoader()获取根目录即resources
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(xmlPath);
        return inputStream;
    }
}
