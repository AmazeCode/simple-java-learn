package com.java.design.pattern.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射实例
 */
public class ReflectDemo {

    /*
            思考:
            1、在构造函数中发生异常对象会创建成功吗？---答案是不会的
            2、如果把构造函数私有化的时候,在当前类反射是可以拿到类的信息的,但是放到其他类是不能拿到类的信息的
            3、怎么防止被反射攻击: 设置成私有的构造函数,在构造函数中抛出异常,防止反射攻击(判断是否创建过对象,如果创建过对象构造函数直接抛出异常)
         */
    public static void main(String[] args) throws Exception{
        //1、初始化的操作，无参构造函数
        /*UserEntity userEntity = new UserEntity();
        userEntity.userName = "蚂蚁视频自学课堂";
        System.out.println(userEntity.userName);*/
        //2、使用Java的反射机制创建对象, forName接收参数是类的完整路径
        Class<?> forName = Class.forName("com.java.design.pattern.reflect.UserEntity");
        //3、使用反射机制创建对象(反射创建对象也会走无参构造函数)
        UserEntity userEntity = (UserEntity)forName.newInstance();
        userEntity.userName = "反射对象";
        System.out.println(userEntity.userName);
        //4、反射的应用场景（jdbc）、spring IOC底层使用的是反射机制+DOM4j实现，框架Hibernate、mybatis使用的是反射
        //5、使用反射机制去获取类的方法信息
        Method[] methods = forName.getMethods();
        for(Method method : methods){
            System.out.println("类的方法："+method.getName());
        }
        //6、获取类的属性、不能使用(getFields)
        Field[] fields = forName.getDeclaredFields();
        for (Field field : fields){
            System.out.println("类的属性："+field.getName());
        }

        // 调用有参构造函数 (注意使用getConstructor(String.class)方式只能够调用共有的有参构造函数,使用getDeclaredConstructor(String.class)并且通过设置constructor.setAccessible(true);允许访问私有即可访问私有构造函数)
        Constructor<?> constructor = forName.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);//允许构造函数私有
        UserEntity user = (UserEntity)constructor.newInstance("自学每特教学视频");
        System.out.println(user.userName);
    }
}
