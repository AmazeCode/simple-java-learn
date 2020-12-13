package com.java.jvm.classload;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义ClassLoader
 */
public class MyClassLoader extends ClassLoader {

    /*
        重写方法
        name：传递一个class路径地址包名(比如说com.java.jvm.classload.MyClassLoader)
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try{
            //1、获取文件名称
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            //2、读取文件流
            InputStream is = this.getClass().getResourceAsStream(fileName);
            //3、读取字节
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            //4、将读取byte数组给jvm识别class对象
            return defineClass(name, bytes, 0, bytes.length);
        }catch (Exception e){
            throw new ClassNotFoundException();
        }
    }
}
