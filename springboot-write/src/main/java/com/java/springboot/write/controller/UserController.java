package com.java.springboot.write.controller;

import com.java.springboot.write.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 跳转页面测试
 * @Author: zhangyadong
 * @Date: 2021/1/20 0020 下午 10:41
 * @Version: v1.0
 */
@Controller
public class UserController {

    @RequestMapping("/pageIndex")
    public String pageIndex() {
        return "pageIndex";
    }
}
