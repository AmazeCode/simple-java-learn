package com.java.springboot.exception.controller;

import com.java.springboot.exception.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

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
        //实际开发中怎么把错误信息记录到日志中?
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
        logger.info("indexThymeleaf 执行了");
        return "/page/indexThymeleaf";
    }


    /**
     * @description: thymeleaf页面传值第一种方式
     * @params: []
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/14 15:30
     */
    @RequestMapping("/dataThymeleafOne")
    public ModelAndView dataThymeleafOne() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("msg","我是传递过来的值。。。。。【thymeleaf页面传值第一种方式】");
            int i = 1/0; //模拟页面访问出错异常
            modelAndView.setViewName("/page/dataThymeleaf");
            return modelAndView;
        } catch (RuntimeException e) {
            throw new MyException("500","系统异常--thymeleaf页面传值第一种方式");
        }
    }

    /**
     * @description: thymeleaf页面传值第二种方式
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
            model.addAttribute("msg","我是传递过来的值。。。。。【thymeleaf页面传值第二种方式】");
            int i = 1/0;
            return "/page/dataThymeleaf";
        } catch (RuntimeException e){
            throw new MyException("500","系统异常--thymeleaf页面传值第二种方式");
        }
    }


    /**
     * @description: thymeleaf第三种向页面传值的方式
     * @params: [modelMap]
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/14 17:54
     */
    @RequestMapping("/dataThymeleafThree")
    public String dataThymeleafThree(ModelMap modelMap) {
        /*
            注意不能直接在方法上抛异常,否则不会走异常处理,而是需要在方法内使用try-catch后再抛出指定的异常,才能正常走异常处理
         */
        try {
            modelMap.put("msg","我是传递过来的值。。。。。【thymeleaf页面传值第三种方式】");
            int i = 1/0;
            return "/page/dataThymeleaf";
        } catch (RuntimeException e){
            throw new MyException("500","系统异常--thymeleaf页面传值第三种方式");
        }
    }
}
