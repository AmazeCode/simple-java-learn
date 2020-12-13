package com.java.jvm.classload;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 热部署入口
 */
public class HotSwap {

    /*public static void main(String[] args) throws Exception{
        User user1 = new User();
        user1.add();
        // 将user2.0版本class文件覆盖user1.0版本class文件
        Thread.sleep(20 * 1000);
        User user2 = new User();
        user2.add();
    }*/

    public static void main(String[] args) throws Exception{
        System.out.println("1.0版本");
        loadUser();
        System.gc();//回收User对象,否则会无法删除替换
        // 需要被修改的class文件
        File file1 = new File("D:\\test\\User.class");
        // 之前的class文件
        File file2 = new File("D:\\IdeaSpace\\simple-java-learn\\jvm\\target\\classes\\com\\java\\jvm\\classload\\User.class");
        // 删除之前的class文件
        boolean isDelete = file2.delete();
        if(!isDelete){
            System.out.println("热部署失败,无法删除文件..");
            return;
        }
        //移动到file2目录(优化的时候可以采用覆盖的形式)
        file1.renameTo(file2);
        System.out.println("2.0版本");
        loadUser();
    }

    /*
        热加载类
     */
    public static void loadUser() throws Exception{
        MyClassLoader myClassLoader = new MyClassLoader();
        //1、使用类加载器读取信息
        Class<?> findClass = myClassLoader.findClass("com.java.jvm.classload.User");
        //2、使用反射机制初始化对象
        Object obj = findClass.newInstance();
        //3、使用反射机制调用方法
        Method method = findClass.getMethod("add");
        method.invoke(obj);
    }
}
