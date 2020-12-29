package com.java.spring.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 自定义Servlet
 * @Author: zhangyadong
 * @Date: 2020/12/27 10:52
 * @Version: v1.0
 */
public class IndexServlet extends HttpServlet {

    /*
        servlet 声明周期：加载-》初始化-》服务-》销毁
     */

    //初始化
    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    //销毁
    @Override
    public void destroy() {
        System.out.println("destroy");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("我是Servlet!");
        resp.getWriter().print("success");
    }
}
