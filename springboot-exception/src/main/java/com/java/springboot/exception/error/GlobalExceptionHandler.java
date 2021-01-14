package com.java.springboot.exception.error;

import com.java.springboot.exception.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 全局捕获异常案例
 * 1.捕获返回json格式
 * 2.捕获返回页面
 * @Author: zhangyadong
 * @Date: 2021/1/14 14:41
 * @Version: v1.0
 */
@ControllerAdvice(basePackages = "com.java.springboot.exception.controller")
public class GlobalExceptionHandler {

    /*
        @ControllerAdvice 增强的 Controller,可以实现三个方面的功能：
        1.全局异常处理
        2.全局数据绑定
        3.全局数据预处理
        @ResponseBody // 返回json格式
        @ModelAndValue //返回页面
     */


    private String errorPage = "error"; // 自定义错误页面/public/error.html

    /**
     * @description: 错误过滤json格式返回
     * @params: []
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @author: zhangyadong
     * @date: 2021/1/14 15:08
     */
    @ExceptionHandler(value = NullPointerException.class) //拦截运行时异常
    @ResponseBody //返回json格式
    public Map<String,Object> errorResult() {
        Map<String,Object> errorResultMap = new HashMap<String,Object>();
        errorResultMap.put("errorCode","500");
        errorResultMap.put("errorMsg","全局捕获异常系统错误");
        return errorResultMap;
    }

    /**
     * @description: 错误过滤跳转页面
     * @params: []
     * @return: java.lang.Object
     * @author: zhangyadong
     * @date: 2021/1/14 15:09
     */
    @ExceptionHandler(MyException.class)
    public Object errorPage() {
        ModelAndView mav = new ModelAndView();
        MyException myException = new MyException("500","系统异常");
        mav.addObject("data",myException);
        mav.setViewName(errorPage);
        return mav;
    }
}
