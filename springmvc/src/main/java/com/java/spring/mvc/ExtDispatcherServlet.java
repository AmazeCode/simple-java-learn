package com.java.spring.mvc;

import com.java.spring.mvc.annotation.ExtController;
import com.java.spring.mvc.annotation.ExtRequestMapping;
import com.java.spring.util.ClassUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class ExtDispatcherServlet extends HttpServlet {

    //springmvc 容器对象 key:类名小写 value：对象
    private ConcurrentHashMap<String,Object> springmvcBeans = new ConcurrentHashMap<String,Object>();
    //springmvc 容器对象 key:请求地址 value：类
    private ConcurrentHashMap<String,Object> urlBeans = new ConcurrentHashMap<String,Object>();
    //springmvc 容器对象 key:请求地址 value：方法名称
    private ConcurrentHashMap<String,String> urlMethods = new ConcurrentHashMap<String,String>();
    //实际做的时候可以这么去定义:key：请求地址 value：方法名称,参数
    //private ConcurrentHashMap<String,ConcurrentHashMap<String,Object>> realUrlMethods = new ConcurrentHashMap<String,ConcurrentHashMap<String,Object>>();

    /*
        参数绑定--反射机制在jdk1.8之前有一个缺点:不能够获取方法参数名称,自己手些需要注意
        springmvc源码中如果判断是jdk1.7时,会采用 asm字节码技术,获取方法参数名称,如果判断是1.8时,则会采用jdk1.8的反射机制去获取
     */

    @Override
    public void init() throws ServletException {
        // 1.获取包下所有的类
        List<Class<?>> classes = ClassUtil.getClasses("com.java.spring.mvc.controller");
        // 2.将扫包范围所有的类,注入springmvc容器里面，存放在map集合中 key为默认类名小写，value为对象
        try {
            findClassMvcAnnotation(classes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 3.将url映射和方法进行关联
        handlerMapping();
    }

    /**
     * @description: 2、将扫包范围所有的类,注入springmvc容器里面，存放在map集合中 key为默认类名小写，value为对象
     * @params: [classes]
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/29 11:52
     */
    private void findClassMvcAnnotation(List<Class<?>> classes) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (Class<?> classInfo : classes){
            //判断类上是否加上注解
            ExtController extController = classInfo.getDeclaredAnnotation(ExtController.class);
            if(extController != null){
                //默认类名小写
                String beanId = ClassUtil.toLowerCaseFirstOne(classInfo.getName());
                //实例化对象
                Object object = ClassUtil.newInstance(classInfo);
                // 格式:类名小写,类对象
                springmvcBeans.put(beanId,object);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //####################### 处理请求 #######################
        //1、获取请求的地址,仅仅时请求地址(不包含Ip和项目名称)
        String requestPath = req.getServletPath();
        if (StringUtils.isEmpty(requestPath)) {
            return;
        }
        //2、从Map集合中获取控制对象(springmvc中叫做获取hander对象)
        Object object = urlBeans.get(requestPath);
        if (object == null) {
            resp.getWriter().println(" not found 404 url");
            return;
        }
        //3、使用url地址获取方法
        String methodName = urlMethods.get(requestPath);
        if (StringUtils.isEmpty(methodName)) {
            resp.getWriter().println(" not found 404 method");
            return;
        }
        //4、使用java的反射机制调用方法,并且获取方法返回结果
        String resultPage = (String) methodInvoke(object, methodName);
        //5、调用视图转换器渲染给页面展示
        extResourceViewResolver(resultPage,req,resp);
    }

    /**
     * @description: 自定义视图解析器
     * @params: [pageName, req, res]
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/29 17:44
     */
    private void extResourceViewResolver(String pageName, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //根路径
        String prefix = "/";
        String suffix = ".jsp";
        req.getRequestDispatcher(prefix + pageName + suffix).forward(req, res);
    }

    /**
     * @description: 使用java反射机制调用方法
     * @params: [object, methodName]
     * @return: java.lang.Object
     * @author: zhangyadong
     * @date: 2020/12/29 16:23
     */
    private Object methodInvoke(Object object, String methodName){
        try {
            Class<?> classInfo = object.getClass();
            Method method = classInfo.getMethod(methodName);
            //调用方法,invoke可以传递参数
            Object result = method.invoke(object);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @description: 3、将url映射和方法进行关联
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/29 15:32
     */
    private void handlerMapping(){
        //1、遍历springmvc bean容器,判断类上是否有url映射注解
        for (Map.Entry<String,Object> mvcBean : springmvcBeans.entrySet()) {
            //2、遍历所有类上是否有url映射注解
            //获取bean对象
            Object object = mvcBean.getValue();
            Class<?> classInfo = object.getClass();
            ExtRequestMapping urlExtRequestMapping = classInfo.getDeclaredAnnotation(ExtRequestMapping.class);
            String baseUrl = "";
            if(urlExtRequestMapping != null){
                //获取类上的url映射地址
                baseUrl = urlExtRequestMapping.value();
            }
            //3、判断方法上是否有加url映射地址
            Method[] declaredMethods = classInfo.getDeclaredMethods();

            for (Method method : declaredMethods) {
                //判断方法上是否有加url映射注解
                ExtRequestMapping methodExtRequestMapping = method.getDeclaredAnnotation(ExtRequestMapping.class);
                if(methodExtRequestMapping != null){
                    String methodUrl = baseUrl + methodExtRequestMapping.value();
                    //springmvc 容器对象 key:请求地址 value：类
                    urlBeans.put(methodUrl,object);
                    //springmvc 容器对象 key:请求地址 value：方法名称
                    urlMethods.put(methodUrl,method.getName());
                }
            }
        }
    }
}
