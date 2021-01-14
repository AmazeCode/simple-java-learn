package com.java.springboot.exception.controller;

import com.java.springboot.exception.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 全局捕获异常案例
 * @Author: zhangyadong
 * @Date: 2021/1/14 13:50
 * @Version: v1.0
 */
@Controller
public class ErrorController {

    /*
        如果每个方法都可能发生异常,每个方法上都加上try,这样做肯定不好?怎么做?
        全局捕获异常 使用AOP技术 采用异常通知
     */
    
    /**
     * @description: 返回json出错
     * @params: []
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/14 17:20
     */
    @ResponseBody
    @RequestMapping("/getUser")
    public String getUser() {
        throw new NullPointerException(); //模拟json接口访问异常
    }

    /**
     * @description: 直接访问thymeleaf页面不传值
     * @params: []
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/14 15:24
     */
    @RequestMapping("/indexThymeleaf")
    public String indexThymeleaf() throws MyException {
        return "/page/indexThymeleaf";
    }


    /**
     * @description: thymeleaf页面传值(方式一)
     * @params: []
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/14 15:30
     */
    @RequestMapping("/dataThymeleafOne")
    public ModelAndView dataThymeleafOne() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("msg","我是传递过来的值。。。。。【第一种写法】");
            int i = 1/0; //模拟页面访问出错异常
            modelAndView.setViewName("/page/dataThymeleaf");
            return modelAndView;
        } catch (RuntimeException e) {
            throw new MyException("500","系统异常--第一种访问页面方式");
        }
    }

    /**
     * @description: thymeleaf页面传值(方式二)
     * @params: [model]
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/14 15:34
     */
    @RequestMapping("/dataThymeleafTwo")
    public String dataThymeleafTwo(Model model) {
        /*
            注意不能直接在方法上抛异常,否则不会走异常处理,而是需要在方法内使用try-catch后再抛出指定的异常,才能正常走异常处理
         */
        try {
            model.addAttribute("msg","我是传递过来的值。。。。。【第二种写法】");
            int i = 1/0;
            return "/page/dataThymeleaf";
        } catch (RuntimeException e){
            throw new MyException("500","系统异常--第二种访问页面方式");
        }
    }
}
