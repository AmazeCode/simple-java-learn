package com.java.jvm.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.reflect.Method;

/**
 * 动态修改字节码文件
 */
public class DynamicModifyByteCodeFile {

    public static void main(String[] args)throws Exception {
        ClassPool pool = ClassPool.getDefault();
        //1、获取User类、存放包路径
        CtClass userClass = pool.get("com.java.jvm.bytecode.User");
        //2、创建方法
        CtMethod method = new CtMethod(CtClass.voidType,"sum",new CtClass[]{CtClass.intType,CtClass.intType},userClass);
        //3、添加方法体,注意一定要使用$1和$2否则程序识别不了
        method.setBody("System.out.println(\"sum:\"+($1+$2));");
        //4、添加方法
        userClass.addMethod(method);
        //5、生成clas文件
        userClass.writeFile("D:/test");

        //6、动态执行方法
        Class<?> forName = userClass.toClass();
        Object newInstance = forName.newInstance();//调用默认构造函数
        Method sumMethod = forName.getDeclaredMethod("sum",int.class,int.class);
        //执行方法,传入构造方法和参数
        sumMethod.invoke(newInstance,1,4);
        //怎么使用javassist 实现动态代理
    }
}
