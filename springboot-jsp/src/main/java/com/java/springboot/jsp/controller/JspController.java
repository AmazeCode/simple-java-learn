package com.java.springboot.jsp.controller;

import com.java.springboot.jsp.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 这是Jsp整合SpringBoot
 * @Author: zhangyadong
 * @Date: 2021/1/13 0013 下午 9:43
 * @Version: v1.0
 */
@Controller
public class JspController {

    /*
        注意: 使用List<User>封装数据传入到前台时,User不能放再当前Controller中,否则jsp中使用${user.id}获取不到值
     */

    @RequestMapping("/jspIndex")
    public ModelAndView jspIndex() {
        //构建测试数据
        List<User> userList = new ArrayList<User>();
        User u1 = new User(1, "Tom", "America");
        User u2 = new User(2, "LeiLi", "China");
        userList.add(u1);
        userList.add(u2);
        //创建一个模型视图对象
        ModelAndView mav = new ModelAndView();
        // 将数据放入到ModelAndView中
        mav.addObject("userList",userList);
        // 指定jspIndex.jsp视图接受model
        mav.setViewName("jspIndex");
        return mav;
    }
}