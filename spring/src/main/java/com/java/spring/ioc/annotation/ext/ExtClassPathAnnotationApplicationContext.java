package com.java.spring.ioc.annotation.ext;

import com.java.spring.ioc.annotation.util.ClassUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 手写Spring IoC容器框架,注解版本
 * @Author: zhangyadong
 * @Date: 2020/12/26 12:24
 * @Version: v1.0
 */
public class ExtClassPathAnnotationApplicationContext {

    // 扫包范围
    private String packageName;

    // 定义SpringBean 容器
    private ConcurrentHashMap<String,Object> beans = null;

    /**
     * @description: 构造方法
     * @params: [packageName]
     * @return: 
     * @author: zhangyadong
     * @date: 2020/12/26 12:28
     */
    public ExtClassPathAnnotationApplicationContext(String packageName) throws Exception {
        beans = new ConcurrentHashMap<String,Object>();
        this.packageName = packageName;
        initBean();
        initEntryField();
    }

    /**
     * @description: 初始化属性
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/26 17:02
     */
    private void initEntryField() throws Exception {
        // 1.遍历所有的Bean容器对象
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            // 2.判断属性上面是否有加注解
            Object bean = entry.getValue();
            attriAssign(bean);
        }
    }

    /**
     * @description: 获取Bean
     * @params: [beanId]
     * @return: java.lang.Object
     * @author: zhangyadong
     * @date: 2020/12/26 15:09
     */
    public Object getBean(String beanId) throws Exception{
        if(StringUtils.isEmpty(beanId)){
            throw new Exception("beanId参数不能为空！");
        }
        // 1.从spring容器获取bean
        Object object = beans.get(beanId);
        /*if(object == null){
            throw new Exception("没有找到该Bean对象");
        }*/
        // 2.使用反射机制初始化对象
        return object;
    }

    public Object newInstance(Class<?> classInfo) throws Exception{
        //调用参构造函数
        Constructor<?> constructor = classInfo.getDeclaredConstructor(null);
        //允许访问私有构造函数
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    /**
     * @description: 初始化对象
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/26 15:27
     */
    public void initBean() throws Exception {
        // 1.使用java反射机制扫包,获取包下所有的类
        List<Class<?>> classes = ClassUtil.getClasses(packageName);
        // 2.判断类上是否存在注入bean的注解
        ConcurrentHashMap<String, Object> classExistAnnotation = findClassExistAnnotation(classes);
        if(classExistAnnotation == null || classExistAnnotation.isEmpty()){
            throw new Exception("该包上没有任何类加上注解");
        }
    }

    /**
     * @description: 判断类上是否存在注入bean的注解
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/26 14:36
     */
    public ConcurrentHashMap<String,Object> findClassExistAnnotation(List<Class<?>> classes) throws Exception {

        for (Class<?> classInfo : classes){
            //判断类上是否有注解
            ExtService annotation = classInfo.getAnnotation(ExtService.class);
            if(annotation != null){
                //获取当前类名  beanid 默认类名小写,有值时取值需扩展
                String className = classInfo.getSimpleName();
                //将当前类名变为小写
                String beanId = ClassUtil.toLowerCaseFirstOne(className);
                //获取实例
                Object newInstance = newInstance(classInfo);
                //存放beanid
                beans.put(beanId,newInstance);
            }
        }
        return beans;
    }

    /**
     * @description: 依赖注入注解原理
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2020/12/26 16:23
     */
    public void attriAssign(Object object) throws Exception {
        // 1.使用反射机制获取当前类的属性
        Class<?> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();
        // 2.判断当前类属性是否存在注解
        for (Field field : declaredFields){
            ExtResource extResource = field.getAnnotation(ExtResource.class);
            if (extResource != null) {
                // 获取属性名称
                String beanId = field.getName();
                Object bean = getBean(beanId);
                if (bean != null) {
                    // 3.默认使用属性名称,查找bean容器对象 1参数 当前对象 2参数给属性赋值
                    field.setAccessible(true);//允许访问私有属性
                    field.set(object,bean);
                }
            }
        }
    }
}
