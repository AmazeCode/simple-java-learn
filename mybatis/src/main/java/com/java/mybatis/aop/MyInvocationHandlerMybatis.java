package com.java.mybatis.aop;

import com.java.mybatis.annotation.ExtInsert;
import com.java.mybatis.annotation.ExtParam;
import com.java.mybatis.annotation.ExtSelect;
import com.java.mybatis.entity.User;
import com.java.mybatis.utils.JdbcUtils;
import com.java.mybatis.utils.SqlUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 使用反射动态代理技术 拦截接口方法
 * @Author: zhangyadong
 * @Date: 2021/1/2 17:14
 * @Version: v1.0
 */
public class MyInvocationHandlerMybatis implements InvocationHandler {

    private Object object;

    public MyInvocationHandlerMybatis(Object object) {
        this.object = object;
    }

    /*
            proxy:代理对象, method:拦截方法, args:方法上的参数值
         */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("使用动态代理技术拦截接口方法");
        //1. 判断方法上是否存在@ExtInsert注解
        ExtInsert extInsert = method.getDeclaredAnnotation(ExtInsert.class);
        if (extInsert != null) {
            return extInsertSQL(extInsert,proxy,method,args);
        }
        // 2.查询思路
        // method.getDeclaredAnnotation(ExtSelect.class);
        // 1.判断方法上是否存在注解
        ExtSelect extSelect = method.getDeclaredAnnotation(ExtSelect.class);
        if (extSelect != null) {
            // 2.获取注解上查询的sql语句
            String selectSql = extSelect.value();
            // 3.获取方法上的参数,绑定在一起
            ConcurrentHashMap<String, Object> paramsMap = paramsMap(proxy, method, args);
            // 4.替换成？传递方式
            List<String> sqlSelectParameter = SqlUtils.sqlSelectParameter(selectSql);
            // 5.传递参数
            List<Object> sqlParams = new ArrayList<>();
            for (String paramName : sqlSelectParameter) {
                Object paramValue = paramsMap.get(paramName);
                sqlParams.add(paramValue);
            }
            // 6.将sql语句替换成？
            String newSql = SqlUtils.paramQuestion(selectSql, sqlSelectParameter);
            // 5.调用jdbc代码底层执行sql语句
            // 查询返回对象思路: 1.使用反射机制获取方法类型 2.判断是否有结果集,如果有结果集，再进行初始化 3.使用反色机制，给对象赋值
            ResultSet res = JdbcUtils.query(newSql, sqlParams.toArray());
            // 6.使用反射机制实例对象 ### 获取方法返回的类型,进行实例化
            // 判断是否存在值
            if (!res.next()) {
                return null;
            }
            //下标上移动一位
            res.previous();
            // 使用反射机制获取方法的类型
            Class<?> returnType = method.getReturnType();
            Object object = returnType.newInstance();
            while (res.next()) {
                // 获取当前所有属性
                Field[] declaredFields = returnType.getDeclaredFields();
                for (Field field : declaredFields) {
                    String fieldName = field.getName();
                    Object fieldValue = res.getObject(fieldName);
                    field.setAccessible(true);
                    field.set(object,fieldValue);
                }
                /*for (String parameterName : sqlSelectParameter) {
                    // 获取参数值
                    Object resultValue = res.getObject(parameterName);
                    // 使用java的反射机制赋值
                    Field field = returnType.getDeclaredField(parameterName);
                    //允许访问构造函数私有方法
                    field.setAccessible(true);
                    field.set(object,resultValue);
                }*/
            }
            return object;
        }
        return null;
    }

    /**
     * @description: 封装添加方法
     * @params: [extInsert, proxy, method, args]
     * @return: java.lang.Object
     * @author: zhangyadong
     * @date: 2021/1/2 21:45
     */
    private Object extInsertSQL(ExtInsert extInsert, Object proxy, Method method, Object[] args) {
        // 方法上存在@ExtInsert,获取他的SQL语句
        //2. 获取SQL语句，获取注解Insert语句
        String insertSql = extInsert.value();
        System.out.println(insertSql);
        //3. 获取方法参数和SQL参数进行匹配
        ConcurrentHashMap<String, Object> paramsMap = paramsMap(proxy, method, args);
        // 存放sql执行的参数----参数绑定过程
        String[] sqlInsertParameters = SqlUtils.sqlInsertParameter(insertSql);
        List<Object> sqlParams = sqlParam(sqlInsertParameters,paramsMap);
        //4. 替换参数为”？“
        String newSql = SqlUtils.paramQuestion(insertSql, sqlInsertParameters);
        System.out.println(newSql);
        //5. 调用jdbc底层代码执行语句
        return JdbcUtils.insert(newSql, sqlParams);
    }

    /**
     * @description: 获取方法参数和SQL参数进行匹配
     * @params: [proxy, method, args]
     * @return: java.util.concurrent.ConcurrentHashMap<java.lang.String,java.lang.Object>
     * @author: zhangyadong
     * @date: 2021/1/2 21:34
     */
    private ConcurrentHashMap<String,Object> paramsMap (Object proxy, Method method, Object[] args) {
        // 定义一个Map集合 Key为#ExtParam Value，Value结果为参数值
        ConcurrentHashMap<String,Object> paramsMap = new ConcurrentHashMap<>();
        // 获取方法上的参数
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            // 获取
            ExtParam extParam = parameter.getDeclaredAnnotation(ExtParam.class);
            if (extParam != null) {
                String paramName = extParam.value();
                Object paramValue = args[i];
                System.out.println(paramName + "," + paramValue);
                paramsMap.put(paramName, paramValue);
            }
        }
        return paramsMap;
    }

    /**
     * @description: 存放sql执行的参数----参数绑定过程
     * @params: [insertSql, paramsMap]
     * @return: java.util.List<java.lang.Object>
     * @author: zhangyadong
     * @date: 2021/1/2 21:36
     */
    private List<Object> sqlParam(String[] sqlInsertParameters, ConcurrentHashMap<String,Object> paramsMap) {
        List<Object> sqlParams = new ArrayList<>();
        for (String paramName : sqlInsertParameters) {
            Object paramValue = paramsMap.get(paramName);
            sqlParams.add(paramValue);
        }
        return sqlParams;
    }
}
