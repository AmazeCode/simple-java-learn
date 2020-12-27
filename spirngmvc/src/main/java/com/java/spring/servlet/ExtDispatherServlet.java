package com.java.spring.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 自定义前端控制器原理分析
 * 1.创建一个前端控制器 ExtDispatherServlet 拦截所有请求(springmvc 基于servlet实现) </br>
 * 2.初始化操作,重写servlet init方法 </br>
 *   2.1 将扫包范围所有的类,注入springmvc容器里面，存放在map集合中 key为默认类名小写，value为对象</br>
 *   2.2 将url映射和方法进行关联</br>
 *      2.2.1 解析类上是否有注解,使用java反射机制遍历方法，判断方法上是否存在注解，进行封装url和方法对应存入集合中 </br>
 * 3.处理请求,重写Get和Post方法
 *  3.1 获取请求url,从urlBeans集合获取实例对像,获取成功实例对象后,调用urlMethods集合获取方法名称,使用反射机制执行
 */
public class ExtDispatherServlet  extends HttpServlet {

    @Override
    public void init() throws ServletException {
        // 1.获取包下所有的类
        // 2.将扫包范围所有的类,注入springmvc容器里面，存放在map集合中 key为默认类名小写，value为对象
        // 3.将url映射和方法进行关联
    }
}
