package com.java.springboot.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 这是Jsp整合SpringBoot
 * @Author: zhangyadong
 * @Date: 2021/1/13 0013 下午 9:43
 * @Version: v1.0
 */
@Controller
public class JspController {

    @RequestMapping("/jsp/index")
    public String jspIndex() {
        return "jspIndex";
    }
}
