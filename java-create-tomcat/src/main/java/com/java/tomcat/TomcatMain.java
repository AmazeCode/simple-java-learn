package com.java.tomcat;

/**
 * 使用Java语言创建Tomcat服务器 </br>
 * Tomcat 底层执行程序最终执行servlet </br>
 *
 */

import com.java.tomcat.servlet.IndexServlet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * 使用Java语言创建Tomcat服务器 </br>
 * Tomcat 底层执行程序最终执行servlet </br>
 * SpringMVC底层使用servlet包装 </br>
 * @author: zhangyadong
 * @date: 2021/1/20 11:30
 */
public class TomcatMain {

    private static int PORT = 8080;

    private static String CONTEXT_PATH = "/index";

    private static String SERVLET_NAME = "indexServlet";

    public static void main(String[] args) throws Exception{
        // 创建Tomcat服务器
        Tomcat tomcatServer = new Tomcat();
        // 指定端口号
        tomcatServer.setPort(PORT);
        // 是否设置自动部署
        tomcatServer.getHost().setAutoDeploy(false);

        StandardContext standardContext = new StandardContext();
        // 创建上下文
        standardContext.setPath(CONTEXT_PATH);
        // 监听上下文
        standardContext.addLifecycleListener(new Tomcat.FixContextListener());
        // tomcat容器添加standardContext
        tomcatServer.getHost().addChild(standardContext);

        // 创建Servlet
        tomcatServer.addServlet(CONTEXT_PATH,SERVLET_NAME,new IndexServlet());
        // servlet url映射
        standardContext.addServletMappingDecoded("/index",SERVLET_NAME);
        tomcatServer.start();
        System.out.println("Tomcat服务器启动成功");

        // 异步进行接收请求
        tomcatServer.getServer().await();
    }
}
