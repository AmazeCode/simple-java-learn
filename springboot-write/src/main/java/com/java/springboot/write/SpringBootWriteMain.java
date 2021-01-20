package com.java.springboot.write;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * @Description: 纯手写springboot
 * @Author: zhangyadong
 * @Date: 2021/1/20 16:57
 * @Version: v1.0
 */
public class SpringBootWriteMain {

    public static void main(String[] args) throws Exception {
        /*
            使用Java内置Tomcat运行SpringMVC框架
            原理: tomcat加载到 springmvc注解启动方式,就会创建springmvc容器
         */
        start();
    }

    public static void start() throws Exception{
        // 创建Tomcat服务器
        Tomcat tomcatServer = new Tomcat();
        // 指定端口号
        tomcatServer.setPort(9090);
        // 读取项目路径 加载静态资源
        StandardContext ctx = (StandardContext)tomcatServer.addWebapp("/",new File("springboot-write/src/main").getAbsolutePath());
        // 禁止重新载入
        ctx.setReloadable(false);
        // class文件读取地址
        File additionWebInfClass = new File("target/classes");
        // 创建WebRoot
        WebResourceRoot resources = new StandardRoot(ctx);
        // tomcat内部读取Class执行
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",additionWebInfClass.getAbsolutePath(),"/"));
        // 是否设置自动部署
        //tomcatServer.getHost().setAutoDeploy(false);
        tomcatServer.start();
        System.out.println("Tomcat服务器启动成功");

        // 异步进行接收请求
        tomcatServer.getServer().await();
    }
}
